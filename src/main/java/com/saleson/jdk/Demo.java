package com.saleson.jdk;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author saleson
 * @date 2022-03-31 20:59
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        int c = 0;
        AtomicInteger ac = new AtomicInteger();
        while (true) {
            c++;
            int d = ac.incrementAndGet();
            System.out.println(String.format("c:%d, ac:%d", c, d));
            System.out.println(System.currentTimeMillis());
            Thread.sleep(2000);
        }
//        LockSupport.park();
    }
}
