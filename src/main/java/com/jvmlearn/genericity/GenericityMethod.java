package com.jvmlearn.genericity;

/**
 * 泛型方法
 */
public class GenericityMethod {

    public static <T extends Number> double add(T num1, T num2) {
        return num1.doubleValue() + num2.doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(add(1.1, 2));


        new Thread().start();
    }
}
