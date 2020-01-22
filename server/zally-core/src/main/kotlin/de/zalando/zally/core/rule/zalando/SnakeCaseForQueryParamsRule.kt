package de.zalando.zally.core.rule.zalando

import com.typesafe.config.Config
import de.zalando.zally.core.rule.CaseChecker
import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Context
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.Severity
import de.zalando.zally.rulemodels.api.Violation

/**
 * Lint for snake case for query params
 */
@Rule(
    ruleSet = ZalandoRuleSet::class,
    id = "130",
    severity = Severity.MUST,
    title = "Use snake_case (never camelCase) for Query Parameters"
)
class SnakeCaseForQueryParamsRule(config: Config) {

    val description = "Query parameter has to be snake_case"

    private val checker = CaseChecker.load(config)

    @Check(severity = Severity.MUST)
    fun checkQueryParameter(context: Context): List<Violation> =
        checker.checkQueryParameterNames(context).map { Violation(description, it.pointer) }
}
