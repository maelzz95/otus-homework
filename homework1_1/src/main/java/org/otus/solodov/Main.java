package org.otus.solodov;

public class Main {
    public static void main(String[] args) {
        TestRunner.run(TestSuite.class);
        TestRunner.run(DisabledTestSuite.class);
    }
}