package org.example

import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.then
import org.http4k.filter.MicrometerMetrics
import org.http4k.filter.ServerFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.ApacheServer
import org.http4k.server.asServer

fun main() {
    val metricsRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)

    val routes =
        routes(
            "/foo" bind Method.GET to { _ ->
                Response(Status.OK).body("Hello")
            },

            "/metrics" bind Method.GET to { _ ->
                Response(Status.OK).body(metricsRegistry.scrape())
            }
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
