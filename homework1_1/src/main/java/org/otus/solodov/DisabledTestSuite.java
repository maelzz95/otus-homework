package org.otus.solodov;

import org.otus.solodov.annotation.Disabled;
import org.otus.solodov.annotation.Test;

@Disabled(reason = "Disabled test suite")
public class DisabledTestSuite {

    @Test(priority = 1)
    public static void test1() {
        System.out.println(1);
    }

}
