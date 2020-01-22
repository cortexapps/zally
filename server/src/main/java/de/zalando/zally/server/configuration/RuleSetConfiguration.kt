package de.zalando.zally.server.configuration

import de.zalando.zally.core.rule.zalando.ZalandoRuleSet
import de.zalando.zally.core.rule.zally.ZallyRuleSet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RuleSetConfiguration {
    @Bean
    fun zallyRuleSet() = ZallyRuleSet()

    @Bean
    fun zalandoRuleSet() = ZalandoRuleSet()
}
