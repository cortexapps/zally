package de.zalando.zally.server.dto

import de.zalando.zally.core.rule.Result
import de.zalando.zally.rulemodels.api.Severity

class ViolationsCounter(violations: List<Result>) {

    private val counters = violations.groupingBy { it.violationType }.eachCount()

    operator fun get(severity: Severity): Int = counters[severity] ?: 0
    fun getCounter(violationType: Severity): Int = counters[violationType] ?: 0
}
