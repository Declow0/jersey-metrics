package com.github.jerseymetrics

import io.micrometer.core.instrument.MeterRegistry
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment

@SpringBootTest(
	webEnvironment = WebEnvironment.DEFINED_PORT
)
class JerseyMetricsApplicationTests {

    @Autowired
    lateinit var meterRegistry: MeterRegistry

    @Test
    fun `metrics are doubled`() {
        assertEquals(
            0,
            meterRegistry.find("http.server.requests").timers()
                .map { it.id.tags }.flatten().count { it.key == "uri" }
        )

		val rq = HttpGet("http://localhost:8080/jersey/test")
        val rs = HttpClientBuilder.create().build().execute(rq)
        assertEquals(200, rs.statusLine.statusCode)
        assertEquals("hello", rs.entity.content.readAllBytes().toString(charset = Charsets.UTF_8))

        assertEquals(
            1,
            meterRegistry.find("http.server.requests").timers()
                .map { it.id.tags }.flatten().count { it.key == "uri" }
        )
        // has uri=UNKNOWN from
        //      org.springframework.boot.actuate.autoconfigure.metrics.web.servlet.WebMvcMetricsAutoConfiguration
        // has uri=/jersey/test from
        //      org.springframework.boot.actuate.autoconfigure.metrics.jersey.JerseyServerMetricsAutoConfiguration
    }
}
