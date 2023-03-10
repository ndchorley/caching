package org.example

import io.micrometer.prometheus.PrometheusMeterRegistry
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

fun PrometheusMeterRegistry.createHandler() = { _: Request ->
    Response(Status.OK).body(scrape())
}