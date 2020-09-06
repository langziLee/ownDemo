package com.jvmlearn.genericity;

import java.util.HashMap;
import java.util.Map;

/**
 * 泛型擦除
 */
public class GenericityErasure {

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();
        map.put("name", "XiaoMing");
        System.out.println(map.get("name"));
    }
}
