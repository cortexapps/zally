package de.zalando.zally.server.exception

import org.springframework.http.HttpStatus

class InaccessibleResourceUrlException(message: String, val httpStatus: HttpStatus) : RuntimeException(message)
