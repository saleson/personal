package com.saleson.test.stack.trace;

import org.junit.Test;

/**
 * @author saleson
 * @date 2022-03-29 14:14
 */
public class CallStatkTraceTest {


    private static String[] getCallerInfo() {
        StackTraceElement[] sts = new Throwable().getStackTrace();
        if (sts.length < 3) {
            return null;
        }
        return new String[]{sts[2].getClassName(), sts[2].getMethodName()};
    }

    public static void protectiveMethod() {
        String[] celler = getCallerInfo();
        System.out.println(celler[0] + "#" + celler[1]);
    }


    @Test
    public void test1() {
        protectiveMethod();
    }

}
