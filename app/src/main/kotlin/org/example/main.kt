package org.example

import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.ApacheServer
import org.http4k.server.asServer

fun main() {
    val app =
        routes(
            "/" bind Method.GET to { _ ->
                println("Received request to /")
                Response(Status.OK).body("Hello")
            }
        )

    println("Starting app")

    app
        .asServer(ApacheServer(port = 8080))
        .start()
}
