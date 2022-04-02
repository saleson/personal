package com.saleson.test;

import org.junit.Test;

/**
 * @author saleson
 * @date 2022-03-14 22:12
 */
public class OxTest {

    @Test
    public void test1() {
        System.out.println(1 & 1);
        System.out.println(((int) 0x61c88647 * 4) & 15);
        System.out.println(~4);
        p(Integer.toHexString(31));
        System.out.println(~8196);
        System.out.println(Integer.toBinaryString(~8196));
        System.out.println(Integer.toBinaryString(9));
        System.out.println(Integer.toBinaryString(-9));
        System.out.println(Long.toBinaryString(-3l));
        System.out.println(Long.toBinaryString(-3l).length());
    }

    private void p(Object o) {
        System.out.println(o);
    }


    @Test
    public void test2() {
        int rightShift = -10;
        System.out.println("十进制:" + rightShift + ", 二进制:\n" + Integer.toBinaryString(rightShift));
        int newRightShift = rightShift >>> 1;
        System.out.println("右移2位后十进制:" + newRightShift +
                ", 右移2位后二进制:\n" + Integer.toBinaryString(newRightShift));    //右移n位后的云算数x十进制结果，x = x / 2
        System.out.println("int min:" + Integer.MIN_VALUE +
                ", int min二进制:\n" + Integer.toBinaryString(Integer.MIN_VALUE));
        System.out.println("int max:" + Integer.MAX_VALUE +
                ", int max二进制:\n" + Integer.toBinaryString(Integer.MAX_VALUE));    //右移n位后的云算数x十进制结果，x = x / 2
        System.out.println("-1 二进制:\n" + Integer.toBinaryString(-1));
    }

    @Test
    public void test3(){
        p(2<<46);
    }
}
