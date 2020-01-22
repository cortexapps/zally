package de.zalando.zally.core

import com.fasterxml.jackson.databind.JsonNode
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import de.zalando.zally.core.rule.CompositeRulesValidator
import de.zalando.zally.core.rule.ContextRulesValidator
import de.zalando.zally.core.rule.DefaultContextFactory
import de.zalando.zally.core.rule.JsonRulesValidator
import de.zalando.zally.core.rule.ObjectTreeReader
import de.zalando.zally.core.rule.RuleDetails
import de.zalando.zally.core.rule.RulesManager
import de.zalando.zally.core.rule.SwaggerRulesValidator
import de.zalando.zally.rulemodels.api.Rule
import io.swagger.parser.util.ClasspathHelper
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.Paths
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import org.reflections.Reflections
import java.io.StringReader
import kotlin.reflect.full.createInstance


val testConfig: Config by lazy {
    ConfigFactory.load("rules-config.conf")
}

fun getConfigFromContent(content: String): Config = ConfigFactory.parseReader(StringReader(content))

fun getResourceContent(fileName: String): String = ClasspathHelper.loadFileFromClasspath("fixtures/$fileName")

fun getResourceJson(fileName: String): JsonNode = ObjectTreeReader().read(getResourceContent(fileName))

fun openApiWithOperations(operations: Map<String, Iterable<String>>): OpenAPI =
    OpenAPI().apply {
        val pathItem = PathItem()
        operations.forEach { method, statuses ->
            val operation = io.swagger.v3.oas.models.Operation().apply {
                responses = ApiResponses()
                statuses.forEach {
                    responses.addApiResponse(it, ApiResponse())
                }
            }
            pathItem.operation(io.swagger.v3.oas.models.PathItem.HttpMethod.valueOf(method.toUpperCase()), operation)
        }
        paths = Paths()
        paths.addPathItem("/test", pathItem)
    }

val rulesManager: RulesManager by lazy {
    val reflections = Reflections("de.zalando.zally.core")
    val rules = reflections.getTypesAnnotatedWith(Rule::class.java)
    val details = rules
        .filterNotNull()
        .flatMap { instance ->
            instance.getAnnotationsByType(Rule::class.java)
                .map { RuleDetails(it.ruleSet.createInstance(), it, instance) }
        }
    RulesManager(details)
}

val contextRulesValidator: ContextRulesValidator by lazy {
    ContextRulesValidator(rulesManager, DefaultContextFactory())
}

val swaggerRulesValidator: SwaggerRulesValidator by lazy {
    SwaggerRulesValidator(rulesManager)
}

val jsonRulesValidator: JsonRulesValidator by lazy {
    JsonRulesValidator(rulesManager)
}

val compositeRulesValidator: CompositeRulesValidator by lazy {
    CompositeRulesValidator(contextRulesValidator, swaggerRulesValidator, jsonRulesValidator)
}
