package com.chandan.flux;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class Combine {

    public static void main(String[] args) throws Exception {
        Flux<Integer> flux1 = Flux.range(1, 4).delayElements(Duration.ofSeconds(1));
        Flux<Integer> flux2 = Flux.range(5, 4).delayElements(Duration.ofSeconds(2));
        //flux1.mergeWith(flux2).subscribe(System.out::println);

        flux1.concatWith(flux2).subscribe(System.out::println);

        Thread.sleep(15000);
    }
}
