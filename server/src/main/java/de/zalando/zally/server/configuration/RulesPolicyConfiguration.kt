package de.zalando.zally.server.configuration

import de.zalando.zally.core.rule.RulesPolicy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RulesPolicyConfiguration {

    @Value("\${zally.ignoreRules:}")
    private lateinit var ignoredRules: List<String>

    @Bean
    fun rulesPolicy(): RulesPolicy {
        return RulesPolicy(ignoredRules)
    }
}
