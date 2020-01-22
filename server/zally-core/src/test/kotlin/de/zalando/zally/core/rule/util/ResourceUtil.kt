package de.zalando.zally.core.rule.util

import org.apache.commons.io.IOUtils

fun resourceToString(resourceName: String): String {
    return IOUtils.toString(ClassLoader.getSystemResourceAsStream(resourceName))
}

