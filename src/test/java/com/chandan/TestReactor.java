package com.chandan;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class TestReactor {

    @Test
    public void testVerifySuccess() {
        StepVerifier.create(Mono.just("Hello")).expectNext("Hello").expectComplete().verify();
    }

    @Test
    public void testVerifyFailure() {
        StepVerifier.create(Mono.error(new Throwable("Some Error"))).expectError().verify();
    }


}
