package de.zalando.zally.core.rule.zalando

import de.zalando.zally.core.plus
import de.zalando.zally.core.toEscapedJsonPointer
import de.zalando.zally.core.toJsonPointer
import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Context
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.Severity
import de.zalando.zally.rulemodels.api.Violation

@Rule(
    ruleSet = ZalandoRuleSet::class,
    id = "136",
    severity = Severity.MUST,
    title = "Avoid Trailing Slashes"
)
class AvoidTrailingSlashesRule {
    private val description = "Rule avoid trailing slashes is not followed"

    @Check(severity = Severity.MUST)
    fun validate(context: Context): List<Violation> =
        context.validatePaths(
            pathFilter = { (path, _) ->
                path.trim().let { trimmed ->
                    when {
                        trimmed == "/" -> false
                        trimmed.endsWith("/") -> true
                        else -> false
                    }
                }
            }
        ) { (path, _) ->
            context.violations(description, "/paths".toJsonPointer() + path.toEscapedJsonPointer())
        }
}
