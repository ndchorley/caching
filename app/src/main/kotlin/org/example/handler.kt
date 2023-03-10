package org.example

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

fun handle(request: Request): Response {
    val response = Response(Status.OK).body("Hello")

   return  when (request.query("set-cache-headers")) {
        null -> response
        else -> {
            response.header("cache-control", "max-age=300")
        }
    }
}
