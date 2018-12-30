package com.chandan.subsribeon;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import static com.chandan.common.Util.*;

/**
 *
 * Demonstrate the affect of placing subscribeOn at different
 * location of reactive streams, irrespective of placement
 * subscription happens on schedulers.
 * <pre>
 * Starting sleep on First Single -1
 * Starting sleep on main
 * Starting sleep on Second Single -2
 * Finished sleep on First Single -1
 * Finished sleep on Second Single -2
 * Finished sleep on main
 * </pre>
 */
public class SubsribeOn {


    static Mono<String> firstOperatorIsSubscribeOn(final long sleep) {
        return Mono.just("Hello")
                .log()
                .subscribeOn(Schedulers.newSingle("First Single ", false))
                .doOnNext((s) -> {
                    printStartSleep();
                    try {
                        Thread.sleep(sleep);
                        printFinishSleep();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    static Mono<String> lastOperatorIsSubsribeOn(final long sleep) {
        return Mono.just("Hello")
                .log()
                .doOnNext((s) -> {
                    printStartSleep();
                    try {
                        Thread.sleep(sleep);
                        printFinishSleep();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).subscribeOn(Schedulers.newSingle("Second Single ", false));
    }

    public static void main(String[] args) throws Exception {
        firstOperatorIsSubscribeOn(10000).subscribe();
        lastOperatorIsSubsribeOn(10000).subscribe();
        printStartSleep();
        Thread.sleep(11000);
        printFinishSleep();
        System.exit(0);
    }
}
