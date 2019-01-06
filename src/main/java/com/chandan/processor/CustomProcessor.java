package com.chandan.processor;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.util.Loggers;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class CustomProcessor<T> extends FluxProcessor<T, T> {

    private volatile boolean done = false;

    private final AtomicReference<Subscription> upstreamSubscription = new AtomicReference<>();

    private final AtomicReference<CoreSubscriber<? super T>> subscriber = new AtomicReference<>();

    @Override
    public void onSubscribe(Subscription s) {
        upstreamSubscription.compareAndSet(null, s);
    }

    @Override
    public void onNext(T t) {
        Optional.ofNullable(subscriber.get()).ifPresent(s -> s.onNext(t));
    }

    @Override
    public void onError(Throwable t) {
        Optional.ofNullable(subscriber.get()).ifPresent(s -> s.onError(t));
    }

    @Override
    public void onComplete() {
        Optional.ofNullable(subscriber.get()).ifPresent(s -> s.onComplete());
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        if (subscriber.compareAndSet(null, actual)) {
            actual.onSubscribe(new Subscription() {
                private final AtomicReference<Subscription> subscriptionAtomicReference = upstreamSubscription;

                @Override
                public void request(long n) {
                    Optional.ofNullable(subscriptionAtomicReference.get()).ifPresent(s -> s.request(n));
                }

                @Override
                public void cancel() {
                    Optional.ofNullable(subscriptionAtomicReference.get()).ifPresent(s -> s.cancel());
                }
            });
        }
    }

    public static void main(String[] args) {
        Loggers.useSl4jLoggers();
        CustomProcessor<String> processor1 = new CustomProcessor<>();
        Flux.from(processor1).subscribe(System.out::println);
        processor1.onNext("1");
        processor1.onNext("2");
        processor1.onNext("3");
    }
}
