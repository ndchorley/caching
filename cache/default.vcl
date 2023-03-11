vcl 4.0;

backend default {
    .host = "app";
    .port = "8080";
}

sub vcl_recv {
    return(pass);
}
