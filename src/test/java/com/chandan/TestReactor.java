package com.chandan;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class TestReactor {

    @Test
    public void testVerifySuccess() {
        StepVerifier.create(Mono.just("Hello")).expectNext("Hello").expectComplete().verify();
    }

    @Test
    public void testVerifyFailure() {
        StepVerifier.create(Mono.error(new Throwable("Some Error"))).expectNextMatches(e -> Assertions.)
        StepVerifier.create(Mono.error(new Throwable("Some Error"))).expectError().verifyThenAssertThat().;
    }
}
