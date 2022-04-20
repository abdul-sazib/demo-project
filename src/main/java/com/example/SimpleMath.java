package com.example;

public class SimpleMath {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Error:: Divison cannot be 0");
        }
        return a / b;
    }

    public long multiply(int a, int b) {
        return a * b;
    }

    public int remainder(int a, int b) {
        return a % b;
    }
}
