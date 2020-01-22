package de.zalando.zally.core.rule.zalando

import com.typesafe.config.Config
import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Context
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.Severity
import de.zalando.zally.rulemodels.api.Violation
import de.zalando.zally.core.util.getAllHeaders

@Rule(
    ruleSet = ZalandoRuleSet::class,
    id = "166",
    severity = Severity.MUST,
    title = "Avoid Link in Header Rule"
)
class AvoidLinkHeadersRule(rulesConfig: Config) {

    private val headersWhitelist = rulesConfig.getStringList("HttpHeadersRule.whitelist").toSet()

    private val description = "Do Not Use Link Headers with JSON entities"

    @Check(severity = Severity.MUST)
    fun validate(context: Context): List<Violation> {
        val allHeaders = context.api.getAllHeaders()
        return allHeaders
            .filter { it.name !in headersWhitelist && it.name == "Link" }
            .map { context.violation(description, it.element) } // createViolation(context, it) }
    }
}
