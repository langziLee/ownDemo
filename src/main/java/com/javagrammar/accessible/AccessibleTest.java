package com.javagrammar.accessible;

import java.lang.reflect.Field;

/**
 * Accessible的使用
 */
public class AccessibleTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        User user = new User();

        // 反射获取public
        Field field = User.class.getDeclaredField("id");
        System.out.println(field.get(user));

        // 反射获取private
        field = User.class.getDeclaredField("name");
        field.setAccessible(true);  // 禁用访问权限检查
        System.out.println(field.get(user));

        // 反射获取private 的静态对象  TODO
        field = User.class.getDeclaredField("theUser");
        field.setAccessible(true);  // 禁用访问权限检查
        System.out.println(field.get(null));
    }



    static class User {

        private static final User theUser = new User();

        public int id = 123;
        private String name = "Lc";

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
