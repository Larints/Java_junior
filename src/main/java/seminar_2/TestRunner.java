package seminar_2;

import seminar_2.annotations.*;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {
    public static void run(Class<?> testClass) {
        final Object testObj = initTestObj(testClass);
        beforeAll(testClass, testObj);
        test(testClass, testObj);
        afterAll(testClass, testObj);
    }


    private static Object initTestObj(Class<?> testClass) {
        try {
            Constructor<?> noArgsConstructor = testClass.getConstructor();
            return noArgsConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Нет конструктора по умолчанию");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Не удалось создать объект тест класса");
        }
    }


    private static void beforeAll(Class<?> testClass, Object testObj) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.getAnnotation(BeforeAll.class) != null) {
                try {
                    method.invoke(testObj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void beforeEach(Class<?> testClass, Object testObj) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.getAnnotation(BeforeEach.class) != null) {
                try {
                    method.invoke(testObj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void test(Class<?> testClass, Object testObj) {
        for (Method testMethod : testClass.getDeclaredMethods()) {
            if (testMethod.accessFlags().contains(AccessFlag.PRIVATE)) {
                continue;
            }

            if (testMethod.getAnnotation(Test.class) != null) {
                try {
                    beforeEach(testClass, testObj);
                    testMethod.invoke(testObj);
                    afterEach(testClass, testObj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void afterEach(Class<?> testClass, Object testObj) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.getAnnotation(AfterEach.class) != null) {
                try {
                    method.invoke(testObj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void afterAll(Class<?> testClass, Object testObj) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.getAnnotation(AfterAll.class) != null) {
                try {
                    method.invoke(testObj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
