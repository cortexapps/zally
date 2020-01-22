package de.zalando.zally.core.rule

import com.fasterxml.jackson.core.JsonPointer
import de.zalando.zally.rulemodels.api.Severity
import java.net.URI

data class Result(
    val id: String,
    val url: URI,
    val title: String,
    val description: String,
    val violationType: Severity,
    val pointer: JsonPointer,
    val lines: IntRange? = null
)
