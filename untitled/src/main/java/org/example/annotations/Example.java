package org.example.annotations;

public class Example {

    public static void sayHello() {
        System.out.println("Hello!");
    }

    @Test
    public static void testHello() {
        System.out.println("Running testHello()");
    }

    @Test
    private static void testPrivate() {
        System.out.println("Running private test method");
    }

    public void instanceMethod() {
        System.out.println("This won't be called.");
    }
}
