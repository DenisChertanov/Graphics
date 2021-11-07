package ru.dchertanov.splinecurves.util;

@FunctionalInterface
public interface BinaryConsumer<T, U> {
    void apply(T colorRGB, U canvas);
}
