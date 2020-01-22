package de.zalando.zally.core.rule

import de.zalando.zally.core.rulesManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RuleUniquenessTest {
    @Test
    fun ruleIdsShouldBeUnique() {
        val duplicatedCodes = rulesManager.rules
            .filterNot { it.rule.title == "TestUseOpenApiRule" }
            .groupBy { it.rule.id }
            .filterValues { it.size > 1 }

        assertThat(duplicatedCodes)
            .hasToString("{}")
    }

    @Test
    fun ruleTitlesShouldBeUnique() {
        val duplicatedCodes = rulesManager.rules
            .filterNot { it.rule.title == "TestUseOpenApiRule" }
            .groupBy { it.rule.title }
            .filterValues { it.size > 1 }

        assertThat(duplicatedCodes)
            .hasToString("{}")
    }
}
