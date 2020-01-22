package de.zalando.zally.core.rule.zalando

import com.typesafe.config.Config
import de.zalando.zally.core.rule.CaseChecker
import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Context
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.Severity
import de.zalando.zally.rulemodels.api.Violation

@Rule(
    ruleSet = ZalandoRuleSet::class,
    id = "118",
    severity = Severity.MUST,
    title = "Property Names Must be ASCII snake_case"
)
class SnakeCaseInPropNameRule(config: Config) {
    private val description = "Property name has to be snake_case"

    private val checker = CaseChecker.load(config)

    @Check(severity = Severity.MUST)
    fun checkPropertyNames(context: Context): List<Violation> =
        checker.checkPropertyNames(context).map { Violation(description, it.pointer) }
}
