package de.zalando.zally.core.rule

import de.zalando.zally.rulemodels.api.Violation

@Suppress("UndocumentedPublicClass")
object ZallyAssertions {

    fun assertThat(actual: Violation?): ViolationAssert = ViolationAssert(actual)

    fun assertThat(actual: List<Violation>?): ViolationsAssert = ViolationsAssert(actual)
}
