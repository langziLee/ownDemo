package com.jvmlearn.lamdba;

import lombok.extern.slf4j.Slf4j;

/**
 * lamdba 表达式(方法的运用)
 */
@Slf4j(topic = "learn")
public class LamdbaTest02 {

    public static void main(String[] args) {

        // 加 减 乘 除
        log.debug("" + mathOperationAdd.operation(1, 2));
        log.debug("" + mathOperationSubtract.operation(1, 2));

        greetingService.sayMessage("hello");
    }

    static MathOperation mathOperationAdd = (int num1, int num2) -> num1 + num2;
    static MathOperation mathOperationSubtract = (int num1, int num2) -> num1 - num2;

    static GreetingService greetingService = (String str) -> log.debug(str);

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }
}
