package org.example

import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.http4k.core.*
import org.http4k.filter.MicrometerMetrics
import org.http4k.filter.ServerFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.ApacheServer
import org.http4k.server.asServer

fun main() {
    val metricsRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    val serveMetrics = metricsRegistry.createHandler()

    val routes =
        routes(
            "/foo" bind Method.GET to ::handle,
            "/metrics" bind Method.GET to serveMetrics
        )

    val app =
        ServerFilters
            .MicrometerMetrics.RequestCounter(metricsRegistry)
            .then(routes)

    println("Starting app")

    app
        .asServer(ApacheServer(port = 8080, canonicalHostname = "app"))
        .start()
}
