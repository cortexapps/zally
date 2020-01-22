package de.zalando.zally.core.rule

import de.zalando.zally.rulemodels.api.Rule

data class RulesPolicy(val ignoreRules: List<String>) {
    fun accepts(rule: Rule): Boolean {
        return !ignoreRules.contains(rule.id)
    }

    fun withMoreIgnores(moreIgnores: List<String>) = RulesPolicy(ignoreRules + moreIgnores)
}
