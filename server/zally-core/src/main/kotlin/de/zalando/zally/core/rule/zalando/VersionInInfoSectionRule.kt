package de.zalando.zally.core.rule.zalando

import de.zalando.zally.core.toJsonPointer
import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Context
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.Severity
import de.zalando.zally.rulemodels.api.Violation

@Rule(
    ruleSet = ZalandoRuleSet::class,
    id = "116",
    severity = Severity.MUST,
    title = "Use Semantic Versioning"
)
class VersionInInfoSectionRule {
    private val description = "Semantic versioning has to be used in format MAJOR.MINOR(.DRAFT)"
    internal val versionRegex = """^\d+.\d+(.\d+)?$""".toRegex()

    @Check(severity = Severity.MUST)
    fun checkAPIVersion(context: Context): Violation? {
        val version = context.api.info?.version?.trim()
        return when {
            version == null -> context.violation("$description: version is missing", "/info/version".toJsonPointer())
            !version.matches(versionRegex) -> context.violation("$description: incorrect format", "/info/version".toJsonPointer())
            else -> null
        }
    }
}
