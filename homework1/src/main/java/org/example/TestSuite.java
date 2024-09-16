package org.example;

import org.example.annotation.AfterSuite;
import org.example.annotation.BeforeSuite;
import org.example.annotation.Disabled;
import org.example.annotation.Test;
import org.example.exception.TestExecutionException;

public class TestSuite {
    @BeforeSuite
    public static void init() {
        System.out.println("init");
    }

    @Test(priority = 1)
    public static void test1() {
        System.out.println(1);
    }

    @Test(priority = 2)
    @Disabled(reason = "Disabled test")
    public static void test2() {
        System.out.println(2);
    }

    @Test
    public static void test3() {
        throw new TestExecutionException("Failed test");
    }

    @AfterSuite
    public static void close() {
        System.out.println("close");
    }


}
