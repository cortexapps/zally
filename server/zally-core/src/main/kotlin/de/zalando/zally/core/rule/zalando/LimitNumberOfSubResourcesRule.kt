package de.zalando.zally.core.rule.zalando

import com.typesafe.config.Config
import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Context
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.Severity
import de.zalando.zally.rulemodels.api.Violation
import de.zalando.zally.core.util.PatternUtil

@Rule(
    ruleSet = ZalandoRuleSet::class,
    id = "147",
    severity = Severity.SHOULD,
    title = "Limit number of Sub-resources level"
)
class LimitNumberOfSubResourcesRule(rulesConfig: Config) {
    private val subResourcesLimit = rulesConfig.getConfig(javaClass.simpleName).getInt("subresources_limit")
    private val description = "Number of sub-resources should not exceed $subResourcesLimit"

    @Check(severity = Severity.SHOULD)
    fun checkNumberOfSubResources(context: Context): List<Violation> =
        context.api.paths.orEmpty().entries
            .map { (path, pathObj) ->
                Pair(
                    path.split("/").filter { it.isNotEmpty() && !PatternUtil.isPathVariable(it) }.size - 1,
                    pathObj
                )
            }
            .filter { (numberOfSubResources, _) -> numberOfSubResources > subResourcesLimit }
            .map { (_, pathObj) -> context.violation(description, pathObj) }
}
