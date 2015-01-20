package com.variacode.labs.flowmix;

import javax.script.ScriptException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class JavascriptFunctionAggregatorTest {

    public JavascriptFunctionAggregatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test() throws ScriptException {
        System.out.println("JavascriptFunctionAggregatorTest");
        String js = "var sum = 0;"
                + "add = function(fieldValue){ sum = sum + fieldValue; };"
                + "evict = function(fieldValue){ sum = sum - fieldValue; };"
                + "aggregateResult = function(){ return sum; }";
        JavascriptFunctionAggregator<Double, Long> instance = new JavascriptFunctionAggregator<>("stnd", js);
        instance.add((long) 1);
        instance.add((long) 1);
        instance.add((long) 1);
        instance.add((long) 3);
        instance.evict((long) 3);
        instance.add((long) 1);
        Double result = instance.aggregateResult();
        long expectedResult = (long) 4;
        assertEquals(expectedResult, result.longValue());
    }

    @Test
    public void testNoItems() throws ScriptException {
        System.out.println("JavascriptFunctionAggregatorTest");
        String js = "var sum = 0;"
                + "add = function(fieldValue){ sum = sum + fieldValue; };"
                + "evict = function(fieldValue){ sum = sum - fieldValue; };"
                + "aggregateResult = function(){ return sum; }";
        JavascriptFunctionAggregator<Double, Long> instance = new JavascriptFunctionAggregator<>("stnd", js);
        instance.add((long) 1);
        instance.add((long) 1);
        instance.add((long) 1);
        instance.add((long) 3);
        instance.evict((long) 3);
        instance.evict((long) 1);
        instance.evict((long) 1);
        instance.evict((long) 1);
        Double result = instance.aggregateResult();
        long expectedResult = (long) 0;
        assertEquals(expectedResult, result.longValue());
    }

}
