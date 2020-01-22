package de.zalando.zally.rulemodels.api

import com.fasterxml.jackson.core.JsonPointer

data class Violation(
    val description: String,
    val pointer: JsonPointer
)
