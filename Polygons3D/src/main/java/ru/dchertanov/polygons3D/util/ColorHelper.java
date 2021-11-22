package ru.dchertanov.polygons3D.util;

import javafx.scene.paint.Color;

public final class ColorHelper {
    private ColorHelper() {
    }

    public static int getRGBFromColor(Color color) {
        int r = ((int) (color.getRed() * 255));
        int g = ((int) (color.getGreen() * 255));
        int b = ((int) (color.getBlue() * 255));
        return (r << 16) + (g << 8) + b;
    }

    public static Color getColorFromRGB(int rgb) {
        int r = (rgb & (0x00ff0000)) >> 16;
        int g = (rgb & (0x0000ff00)) >> 8;
        int b = rgb & (0x000000ff);

        return Color.rgb(r, g, b);
    }
}
