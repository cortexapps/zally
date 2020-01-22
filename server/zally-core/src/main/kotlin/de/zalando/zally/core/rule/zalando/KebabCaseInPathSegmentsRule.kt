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
    id = "129",
    severity = Severity.MUST,
    title = "Lowercase words with hyphens"
)
class KebabCaseInPathSegmentsRule(config: Config) {

    private val checker = CaseChecker.load(config)

    private val description = "Use lowercase separate words with hyphens for path segments"
    internal val lowerCaseHyphenSeparatedRegex = "^[a-z-]+$".toRegex()

    @Check(severity = Severity.MUST)
    fun checkKebabCaseInPathSegments(context: Context): List<Violation> =
        checker.checkPathSegments(context).map { Violation(description, it.pointer) }
}
