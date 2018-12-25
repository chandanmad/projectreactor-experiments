package com.chandan.common;

public class Util {

    public static void printStartSleep() {
        System.out.println("Starting sleep on " + Thread.currentThread().getName());
    }

    public static void printFinishSleep() {
        System.out.println("Finished sleep on " + Thread.currentThread().getName());
    }

}
