package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test_add() {
        assertEquals(new SimpleMath().add(20, 30), 50);
    }

    @Test
    public void test_subtract() {
        assertEquals(new SimpleMath().subtract(30, 20), 10);
    }

    @Test
    public void test_divide() {
        assertEquals(new SimpleMath().divide(20, 10), 2);
    }

    @Test
    public void test_multiply() {
        assertEquals(new SimpleMath().multiply(1000000000, 1000000000), 1000000000);
    }
}
