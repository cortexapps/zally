package de.zalando.zally.server.util

data class ErrorResponse(
    val title: String? = null,
    val status: String? = null,
    val detail: String? = null
)
