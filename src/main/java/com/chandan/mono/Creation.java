package com.chandan.mono;

import reactor.core.publisher.Mono;
import reactor.util.Loggers;

public class Creation {

    public static void main(String[] args) {
        Loggers.useSl4jLoggers();

        //Empty mono doOnNext never gets called , complete consumer called.
        Mono.empty()
                .log()
                .doOnNext(v -> Loggers.getLogger("STDOUT").debug("doOnNext called for {}", v))
                .subscribe(
                        v -> Loggers.getLogger("STDOUT").debug("Received {}", v),
                        t -> Loggers.getLogger("STDOUT").error("Error received", t),
                        () -> Loggers.getLogger("STDOUT").debug("Completed"));


        //Empty mono doOnNext never gets called , complete consumer called in case of success.
        Mono.error(new Throwable("Error mono"))
                .log()
                .doOnNext(v -> Loggers.getLogger("STDOUT").debug("doOnNext called for {}", v))
                .subscribe(
                        System.out::println,
                        t -> Loggers.getLogger("STDOUT").error("Error received ", t),
                        () -> Loggers.getLogger("STDOUT").debug("Completed"));

        //Place of log does not influence
        Mono.just("Hello")
                .doOnNext(v -> Loggers.getLogger("STDOUT").debug("doOnNext called for {}", v))
                .log()
                .subscribe(
                        v -> Loggers.getLogger("STDOUT").debug("Received {}", v),
                        t -> Loggers.getLogger("STDOUT").error("Error received", t),
                        () -> Loggers.getLogger("STDOUT").debug("Completed"));
    }
}
