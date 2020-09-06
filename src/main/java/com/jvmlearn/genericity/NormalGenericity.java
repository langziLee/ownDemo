package com.jvmlearn.genericity;

/**
 * 泛型类
 *
 * @param <T>
 */
public class NormalGenericity<T> {

    private T mData;

    NormalGenericity() {
    }

    NormalGenericity (T mData) {
        this();
        this.mData = mData;
    }

    public void setT(T pDate) {
        this.mData = pDate;
    }

    public T getT() {
        return this.mData;
    }

    public static void main(String[] args) {

        NormalGenericity wNormalGenericity = new NormalGenericity();
        wNormalGenericity.setT(143234);

        System.out.println(wNormalGenericity.getT());
    }
}
