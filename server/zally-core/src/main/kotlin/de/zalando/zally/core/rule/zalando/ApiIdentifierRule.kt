package de.zalando.zally.core.rule.zalando

import de.zalando.zally.core.toJsonPointer
import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Context
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.Severity
import de.zalando.zally.rulemodels.api.Violation

@Rule(
    ruleSet = ZalandoRuleSet::class,
    id = "215",
    severity = Severity.MUST,
    title = "Provide API Identifier"
)
class ApiIdentifierRule {
    private val apiIdPattern = """^[a-z0-9][a-z0-9-:.]{6,62}[a-z0-9]$""".toRegex()

    private val noApiIdDesc = "API Identifier should be provided"
    private val invalidApiIdDesc = "API Identifier doesn't match the pattern $apiIdPattern"

    private val extensionName = "x-api-id"
    private val extensionPointer = "/info/$extensionName".toJsonPointer()

    @Check(severity = Severity.MUST)
    fun validate(context: Context): Violation? {
        val apiId = context.api.info?.extensions?.get(extensionName)

        return when {
            apiId == null || apiId !is String -> context.violation(noApiIdDesc, extensionPointer)
            !apiId.matches(apiIdPattern) -> context.violation(invalidApiIdDesc, extensionPointer)
            else -> null
        }
    }
}
