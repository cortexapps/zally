package de.zalando.zally.server.dto

import com.fasterxml.jackson.annotation.JsonProperty
import de.zalando.zally.rulemodels.api.Severity

data class RuleDTO(
    val title: String? = null,
    val type: Severity? = null,
    val url: String? = null,
    val code: String? = null,
    @JsonProperty("is_active") val active: Boolean? = null
)
