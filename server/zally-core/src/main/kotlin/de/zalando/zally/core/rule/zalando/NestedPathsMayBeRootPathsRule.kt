package de.zalando.zally.core.rule.zalando

import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Context
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.Severity
import de.zalando.zally.rulemodels.api.Violation
import de.zalando.zally.core.util.PatternUtil.isPathVariable

@Rule(
    ruleSet = ZalandoRuleSet::class,
    id = "145",
    severity = Severity.MAY,
    title = "Consider Using (Non-) Nested URLs"
)
class NestedPathsMayBeRootPathsRule {
    private val description = "Nested paths may be top-level resource"

    @Check(severity = Severity.MAY)
    fun checkNestedPaths(context: Context): List<Violation> =
        context.api.paths.orEmpty().entries
            .map { (path, pathEntry) -> Pair(pathEntry, path.split("/").filter { isPathVariable(it) }.count()) }
            .filter { (_, numberOfPathParameters) -> numberOfPathParameters > 1 }
            .map { (path, _) -> context.violation(description, path) }
}
