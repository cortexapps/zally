package de.zalando.zally.core.rule

import de.zalando.zally.rulemodels.api.Rule
import java.net.URI

/** RuleSet used to contain test rules  */
class TestRuleSet : AbstractRuleSet() {

    override val id: String = javaClass.simpleName

    override val title: String = "Test Rules"

    override val url: URI = URI.create("http://test.example.com/")

    override fun url(rule: Rule): URI {
        return url.resolve(rule.id)
    }
}
