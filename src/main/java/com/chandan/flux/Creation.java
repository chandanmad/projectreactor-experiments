package com.chandan.flux;

import reactor.core.publisher.Flux;

public class Creation {

    public static void main(String[] args) {
        Flux.create(fluxSink -> fluxSink.error(new Throwable("Error")))
                .onErrorReturn("Hi")
                .subscribe(System.out::println);
    }
}
