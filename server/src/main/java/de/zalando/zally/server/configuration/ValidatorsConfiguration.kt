package de.zalando.zally.server.configuration

import de.zalando.zally.core.rule.CompositeRulesValidator
import de.zalando.zally.core.rule.ContextRulesValidator
import de.zalando.zally.core.rule.DefaultContextFactory
import de.zalando.zally.core.rule.JsonRulesValidator
import de.zalando.zally.core.rule.RulesManager
import de.zalando.zally.core.rule.SwaggerRulesValidator
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.regex.Pattern

@Configuration
class ValidatorsConfiguration {
    @Value("\${zally.propagateAuthorizationUrls:}")
    private lateinit var urls: Array<Pattern>

    @Bean
    fun defaultContextFactory() = DefaultContextFactory(urls)

    @Bean
    fun contextRulesValidator(rulesManager: RulesManager, defaultContextFactory: DefaultContextFactory) =
        ContextRulesValidator(rulesManager, defaultContextFactory)

    @Bean
    fun jsonRulesValidator(rulesManager: RulesManager) = JsonRulesValidator(rulesManager)

    @Bean
    fun swaggerRulesValidator(rulesManager: RulesManager) = SwaggerRulesValidator(rulesManager)

    @Bean
    fun compositeRulesValidator(
        contextRulesValidator: ContextRulesValidator,
        jsonRulesValidator: JsonRulesValidator,
        swaggerRulesValidator: SwaggerRulesValidator) =
        CompositeRulesValidator(contextRulesValidator, swaggerRulesValidator, jsonRulesValidator)
}
