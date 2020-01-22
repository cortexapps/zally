package de.zalando.zally.core.rule

import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.RuleSet
import java.lang.reflect.Method

data class CheckDetails(
    val ruleSet: RuleSet,
    val rule: Rule,
    val instance: Any,
    val check: Check,
    val method: Method
)
