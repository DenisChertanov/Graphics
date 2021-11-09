package ru.dchertanov.splinecurves.util;

@FunctionalInterface
public interface TernaryConsumer <T, U, V> {
    void apply(T t, U  u, V v);
}
