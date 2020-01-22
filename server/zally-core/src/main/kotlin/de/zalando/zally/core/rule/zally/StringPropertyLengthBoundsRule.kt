package de.zalando.zally.core.rule.zally

import com.typesafe.config.Config
import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Context
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.Severity
import de.zalando.zally.rulemodels.api.Violation
import de.zalando.zally.core.util.getAllProperties
import io.swagger.v3.oas.models.media.Schema

@Rule(
    ruleSet = ZallyRuleSet::class,
    id = "S007",
    severity = Severity.SHOULD,
    title = "Define bounds for lengths of string properties"
)
class StringPropertyLengthBoundsRule(config: Config) {

    internal val formatWhitelist = config
        .getStringList("StringPropertyLengthBoundsRule.formatWhitelist")
        .toList()

    internal val patternImpliesLimits = config
        .getBoolean("StringPropertyLengthBoundsRule.patternImpliesLimits")

    @Check(severity = Severity.SHOULD)
    fun checkStringLengthBounds(context: Context): List<Violation> =
        context.api
            .getAllProperties()
            .filterValues { schema ->
                when {
                    schema.type != "string" -> false
                    schema.format in formatWhitelist -> false
                    schema.pattern != null && patternImpliesLimits -> false
                    else -> true
                }
            }
            .flatMap { (_, schema) ->
                checkStringLengthBoundsReversed(context, schema) +
                    checkStringLengthBound(context, "minLength", schema.minLength, schema) +
                    checkStringLengthBound(context, "maxLength", schema.maxLength, schema)
            }

    private fun checkStringLengthBoundsReversed(context: Context, schema: Schema<Any>): List<Violation> =
        when {
            schema.minLength != null &&
                schema.maxLength != null &&
                schema.minLength > schema.maxLength
            -> context.violations("minLength > maxLength is invalid", schema)
            else -> emptyList()
        }

    private fun checkStringLengthBound(context: Context, bound: String, value: Int?, schema: Schema<Any>) =
        when {
            value == null -> context.violations("No $bound defined", schema)
            value < 0 -> context.violations("Negative $bound is invalid", schema)
            else -> emptyList()
        }
}
