package org.otus.solodov;

import org.otus.solodov.annotation.AfterSuite;
import org.otus.solodov.annotation.BeforeSuite;
import org.otus.solodov.annotation.Disabled;
import org.otus.solodov.annotation.Test;
import org.otus.solodov.exception.IllegalAnnotationAttributeException;
import org.otus.solodov.exception.IncompatibleAnnotationException;

import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {

    private record MethodRecord(Method method, int priority, boolean isBeforeSuite, boolean isAfterSuite) {}

    private static List<MethodRecord> getMethodRecords(Class<?> testSuiteClass) {
        final int maxTestPriority = 10;
        final int minTestPriority = 1;

        Method[] classMethods = testSuiteClass.getDeclaredMethods();
        List<MethodRecord> methodRecordList = new ArrayList<>();


        for (Method classMethod : classMethods) {
            boolean isBeforeSuite = classMethod.isAnnotationPresent(BeforeSuite.class);
            boolean isAfterSuite = classMethod.isAnnotationPresent(AfterSuite.class);
            boolean isTest = classMethod.isAnnotationPresent(Test.class);

            if (isTest) {
                if ((isBeforeSuite || isAfterSuite))
                    throw new IncompatibleAnnotationException(String.format("%s annotation incompatible with %s/%s annotations", Test.class.getSimpleName(), BeforeSuite.class.getSimpleName(), AfterSuite.class.getSimpleName()));
                else {
                    int testPriority = classMethod.getAnnotation(Test.class).priority();
                    if (testPriority < minTestPriority || testPriority > maxTestPriority) {
                        throw new IllegalAnnotationAttributeException(String.format("%s annotation priority should be in range (%d, %d)", Test.class.getSimpleName(), minTestPriority, maxTestPriority));
                    }
                    methodRecordList.add(new MethodRecord(classMethod, testPriority, false, false));
                }
            }

            if (isBeforeSuite) {
                methodRecordList.add(new MethodRecord(classMethod, maxTestPriority + 1, true, false));
            }
            if (isAfterSuite) {
                methodRecordList.add(new MethodRecord(classMethod, minTestPriority - 1,false, true));
            }
        }
        return methodRecordList;
    }

    public static void run(Class<?> testSuiteClass) {
        String className = testSuiteClass.getSimpleName();

        if (testSuiteClass.isAnnotationPresent(Disabled.class)) {
            System.out.println("*** Class " + className + " execution is disabled. Reason: " + testSuiteClass.getAnnotation(Disabled.class).reason());
            return;
        }

        System.out.println("*** Starting test run for class " + className);

        List<MethodRecord> methodRecordList = getMethodRecords(testSuiteClass);

        if (methodRecordList.stream().filter(MethodRecord::isBeforeSuite).count() > 1) throw new IncompatibleAnnotationException("Test class cannot contain more than one " + BeforeSuite.class.getSimpleName() + " annotation");
        if (methodRecordList.stream().filter(MethodRecord::isAfterSuite).count() > 1) throw new IncompatibleAnnotationException("Test class cannot contain more than one " + AfterSuite.class.getSimpleName() + " annotation");

        java.util.function.Function<Method, TestResult> runTest = (method) -> {
            String methodName = method.getName();
            System.out.println("\nStarting " + methodName + " method execution");
            try {
                if (method.isAnnotationPresent(Disabled.class)) {
                    System.out.printf("Method %s is disabled. Reason: %s%n", methodName, method.getAnnotation(Disabled.class).reason());
                    return TestResult.DISABLED;
                }
                else {
                    method.invoke(testSuiteClass);
                    System.out.println("Success");
                    return TestResult.SUCCESS;
                }
            } catch (Exception e) {
                System.out.println("Failure");
                return TestResult.FAILURE;
            }
        };

        List<TestResult> testResults = new ArrayList<>();
        methodRecordList.sort(Comparator.comparing(MethodRecord::priority, Comparator.reverseOrder()));
        methodRecordList.forEach(methodRecord -> testResults.add(runTest.apply(methodRecord.method)));

        long successTestCnt = testResults.stream().filter(s -> s.equals(TestResult.SUCCESS)).count();
        long failureTestCnt = testResults.stream().filter(s -> s.equals(TestResult.FAILURE)).count();
        long allTestCnt = successTestCnt + failureTestCnt;
        System.out.println("\nSuccessful tests count: " + successTestCnt);
        System.out.println("Failure tests count: " + failureTestCnt);
        System.out.println("All tests count: " + allTestCnt + "\n");

        System.out.println("*** Test run for class " + className + " completed successfully\n");

    }

}
