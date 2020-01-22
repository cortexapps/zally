package de.zalando.zally.server.apireview

import com.fasterxml.jackson.databind.JsonNode
import de.zalando.zally.core.EMPTY_JSON_POINTER
import de.zalando.zally.core.toJsonPointer
import de.zalando.zally.rulemodels.api.Check
import de.zalando.zally.rulemodels.api.Rule
import de.zalando.zally.rulemodels.api.Severity
import de.zalando.zally.rulemodels.api.Violation
import de.zalando.zally.server.util.TestRuleSet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Configuration
class RestApiTestConfiguration {

    @Bean
    @Primary
    @Profile("test")
    fun rules(): Collection<Any> {
        return listOf(
            TestCheckIsOpenApi3(),
            TestCheckAlwaysReport3MustViolations()
        )
    }

    /** Rule used for testing  */
    @Rule(
        ruleSet = TestRuleSet::class,
        id = "TestCheckIsOpenApi3",
        severity = Severity.MUST,
        title = "TestCheckIsOpenApi3"
    )
    class TestCheckIsOpenApi3 {

        @Check(severity = Severity.MUST)
        fun validate(json: JsonNode): Violation? {
            return if ("3.0.0" != json.path("openapi").textValue()) {
                Violation("TestCheckIsOpenApi3", "/openapi".toJsonPointer())
            } else null
        }
    }

    /** Rule used for testing  */
    @Rule(
        ruleSet = TestRuleSet::class,
        id = "TestCheckAlwaysReport3MustViolations",
        severity = Severity.MUST,
        title = "TestCheckAlwaysReport3MustViolations"
    )
    class TestCheckAlwaysReport3MustViolations {

        @Check(severity = Severity.MUST)
        fun validate(@Suppress("UNUSED_PARAMETER") json: JsonNode): Iterable<Violation> {
            return listOf(
                Violation("TestCheckAlwaysReport3MustViolations #1", EMPTY_JSON_POINTER),
                Violation("TestCheckAlwaysReport3MustViolations #2", EMPTY_JSON_POINTER),
                Violation("TestCheckAlwaysReport3MustViolations #3", EMPTY_JSON_POINTER)
            )
        }
    }
}
