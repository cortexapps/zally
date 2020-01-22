package de.zalando.zally.server.exception

class InsufficientTimeIntervalParameterException :
    RuntimeException("TO parameter was supplied without corresponding FROM parameter")
