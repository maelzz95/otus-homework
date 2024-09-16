package org.example;

import org.example.annotation.Disabled;
import org.example.annotation.Test;

@Disabled(reason = "Disabled test suite")
public class DisabledTestSuite {

    @Test(priority = 1)
    public static void test1() {
        System.out.println(1);
    }

}
