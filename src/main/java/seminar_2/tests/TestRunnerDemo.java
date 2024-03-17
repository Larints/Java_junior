package seminar_2.tests;

import seminar_2.annotations.*;

public class TestRunnerDemo {

    // private никому не видно
    // default (package-private) внутри пакета
    // protected внутри пакета + наследники
    // public всем

    public static void main(String[] args) {

        TestRunner.run(TestRunnerDemo.class);
    }

    @Test(order = 1)
    private void test1() {
        System.out.println("test1");
    }

    //  @Test(order = 1)
    @Test(order = 2)
    void test2() {
        System.out.println("test2");
    }

    @Test(order = 3)
    void test3() {
        System.out.println("test3");
    }

    @Test(order = 0)
    void test4() {
        System.out.println("test4");
    }

    @AfterAll
    void afterAll() {
        System.out.println("After All");
    }

    @AfterEach
    void afterEach() {
        System.out.println("After Each");
    }

    @BeforeAll
    void beforeAll() {
        System.out.println("Before All");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before Each");
    }


}
