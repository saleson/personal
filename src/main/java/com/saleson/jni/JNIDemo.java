package com.saleson.jni;

/**
 * reference:
 * https://blog.csdn.net/qq_33177268/article/details/123258377?utm_source=app&app_version=5.2.0
 *
 *
 * @author saleson
 * @date 2022-03-27 20:28
 */
public class JNIDemo {

    //定义一个方法，该方法在C中实现
    public native void testHello();

    public static void main(String[] args) throws InterruptedException {
        //加载C文件
        System.loadLibrary("JNIDemo");
        JNIDemo jniDemo = new JNIDemo();
        while (true){
            jniDemo.testHello();
            Thread.sleep(2000);
        }
    }

}