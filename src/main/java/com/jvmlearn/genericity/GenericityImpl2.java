package com.jvmlearn.genericity;

/**
 * 泛型接口实现类2
 *
 * @param <T>
 */
public class GenericityImpl2<T> implements IGenericity<T>{

    private T mData;

    GenericityImpl2(T mData) {
        this.mData = mData;
    }

    @Override
    public T showHello() {
        return null;
    }
}
