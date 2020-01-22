package de.zalando.zally.core.rule

import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.RuleSet
import java.lang.reflect.Method

data class RuleDetails(
    val ruleSet: RuleSet,
    val rule: Rule,
    val instance: Any
) {
    fun toCheckDetails(check: Check, method: Method): CheckDetails =
        CheckDetails(ruleSet, rule, instance, check, method)
}
