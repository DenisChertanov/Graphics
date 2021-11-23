package ru.dchertanov.splinecurves.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class Combinations {
    private static final int MAX_N = 30;
    private static final List<BigInteger> factorials = new ArrayList<>();

    static {
        BigInteger value = BigInteger.ONE;
        factorials.add(value);
        for (int i = 1; i <= MAX_N; ++i) {
            value = value.multiply(BigInteger.valueOf(i));
            factorials.add(value);
        }
    }

    private Combinations() {
    }

    public static long getCombinations(int n, int k) {
        if (n > MAX_N || k < 0 || k > n) {
            throw new IllegalArgumentException();
        }

        BigInteger nFact = factorials.get(n);
        return nFact
                .divide(factorials.get(k))
                .divide(factorials.get(n - k))
                .longValue();
    }
}
