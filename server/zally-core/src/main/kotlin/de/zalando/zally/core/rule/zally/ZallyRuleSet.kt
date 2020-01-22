package de.zalando.zally.core.rule.zally

import de.zalando.zally.core.rule.AbstractRuleSet
import de.zalando.zally.rulemodels.api.Rule
import java.net.URI

class ZallyRuleSet : AbstractRuleSet() {
    override val id: String = javaClass.simpleName
    override val title: String = "Additional Zally Swagger Rules"
    override val url: URI = URI.create("https://github.com/zalando/zally/blob/master/server/rules.md")
    override fun url(rule: Rule): URI {
        val heading = "${rule.id}: ${rule.title}"
        val ref = heading
            .toLowerCase()
            .replace(Regex("[^a-z0-9]+"), "-")
        return url.resolve("#$ref")
    }
}
