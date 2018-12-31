package com.chandan.mono;

import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.util.Loggers;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

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


        // Mono.create
        Stream<String> stream = Arrays.asList("Hello").stream().onClose(() -> Loggers.getLogger("STDOUT").debug("Stream closed"));
        Consumer<MonoSink<String>> sinkConsumer = sink -> {
            sink.onDispose(() -> stream.close());
            sink.onCancel(() -> stream.close());
            sink.onRequest(l -> Loggers.getLogger("STDOUT").debug("Requested {}", l));
            sink.success(stream.findFirst().orElse("Empty"));
        };
        Mono.create(sinkConsumer)
                .log()
                .subscribe(v -> Loggers.getLogger("STDOUT").debug("Received from create {}", v));
    }
}
