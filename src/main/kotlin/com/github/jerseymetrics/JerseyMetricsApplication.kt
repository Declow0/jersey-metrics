package com.github.jerseymetrics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JerseyMetricsApplication

fun main(args: Array<String>) {
	runApplication<JerseyMetricsApplication>(*args)
}
