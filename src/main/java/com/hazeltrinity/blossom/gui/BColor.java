package com.hazeltrinity.blossom.gui;

public class BColor {

    // Constant Colors
    public static final BColor CLEAR = BColor.ofRGBA(0, 0, 0, 0);

    public static final BColor BLACK = BColor.ofRGB(0, 0, 0);
    public static final BColor WHITE = BColor.ofRGB(255, 255, 255);

    public static final BColor RED = BColor.ofRGB(255, 0, 0);
    public static final BColor LIME = BColor.ofRGB(0, 255, 0);
    public static final BColor BLUE = BColor.ofRGB(0, 0, 255);

    public static final BColor YELLOW = BColor.ofRGB(255, 255, 0);
    public static final BColor CYAN = BColor.ofRGB(0, 255, 255);
    public static final BColor MAGENTA = BColor.ofRGB(255, 0, 255);

    public static final BColor GREY = BColor.ofRGB(128, 128, 128);

    public static final BColor MAROON = BColor.ofRGB(128, 0, 0);
    public static final BColor GREEN = BColor.ofRGB(0, 128, 0);
    public static final BColor NAVY = BColor.ofRGB(0, 0, 128);

    public static final BColor OLIVE = BColor.ofRGB(128, 128, 0);
    public static final BColor PURPLE = BColor.ofRGB(0, 128, 128);
    public static final BColor TEAL = BColor.ofRGB(128, 0, 128);

    public static final BColor FLATBE = BColor.ofHex("F1A7BE");

    private final int r;
    private final int g;
    private final int b;
    private final int a;

    private BColor(int r, int g, int b, int a) {
        this.r = r & 255;
        this.b = b & 255;
        this.g = g & 255;
        this.a = a & 255;
    }

    /**
     * Create a new {@code BColor}.
     *
     * @param r red component [0,1]
     * @param g green component [0,1]
     * @param b blue component [0,1]
     * @param a alpha component [0,1]
     *
     * @return a new {@code BColor}
     */
    public static BColor ofRGBA(double r, double g, double b, double a) {
        return new BColor((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
    }

    /**
     * Create a new {@code BColor}.
     *
     * @param r red component [0,1]
     * @param g green component [0,1]
     * @param b blue component [0,1]
     *
     * @return a new {@code BColor}
     */
    public static BColor ofRGB(double r, double g, double b) {
        return new BColor((int) (r * 255), (int) (g * 255), (int) (b * 255), 255);
    }

    /**
     * Create a new {@code BColor}.
     *
     * @param r red component [0,255]
     * @param g green component [0,255]
     * @param b blue component [0,255]
     * @param a alpha component [0,255]
     *
     * @return a new {@code BColor}
     */
    public static BColor ofRGBA(int r, int g, int b, int a) {
        return new BColor(r, g, b, a);
    }

    /**
     * Create a new {@code BColor}.
     *
     * @param r red component [0,255]
     * @param g green component [0,255]
     * @param b blue component [0,255]
     *
     * @return a new {@code BColor}
     */
    public static BColor ofRGB(int r, int g, int b) {
        return new BColor(r, g, b, 255);
    }

    /**
     * Create a new {@code BColor}.
     *
     * @param h hue component [0,1]
     * @param s saturation component [0,1]
     * @param v value component [0,1]
     *
     * @return a new {@code BColor}
     */
    public static BColor ofHSV(double h, double s, double v) {
        return ofHSVA(h, s, v, 1.0);
    }

    /**
     * Create a new {@code BColor}.
     *
     * @param h hue component [0,1]
     * @param s saturation component [0,1]
     * @param v value component [0,1]
     * @param a alpha component [0,1]
     *
     * @return a new {@code BColor}
     */
    public static BColor ofHSVA(double h, double s, double v, double a) {
        double H = 6 * h;
        double C = v * s;
        double X = C * (1 - Math.abs((H % 2) - 1));

        double R, G, B;

        if (0 <= H && H < 1) {
            R = C;
            G = X;
            B = 0;
        } else if (1 <= H && H < 2) {
            R = X;
            G = C;
            B = 0;
        } else if (2 <= H && H < 3) {
            R = 0;
            G = C;
            B = X;
        } else if (3 <= H && H < 4) {
            R = 0;
            G = X;
            B = C;
        } else if (4 <= H && H < 5) {
            R = X;
            G = 0;
            B = C;
        } else {
            R = C;
            G = 0;
            B = X;
        }

        double m = v - C;

        return ofRGBA(R + m, G + m, B + m, a);
    }

    /**
     * Create a new {@code BColor}.
     *
     * @param color the integer form of the color
     *
     * @return a new {@code BColor}
     */
    public static BColor ofColorRGB(int color) {
        return new BColor(
        color & 255,
        (color >> 8) & 255,
        (color >> 16) & 255,
        255
        );
    }

    /**
     * Create a new {@code BColor}.
     *
     * @param color the integer form of the color with alpha
     *
     * @return a new {@code BColor}
     */
    public static BColor ofColorRGBA(int color) {
        return new BColor(
        color & 255,
        (color >> 8) & 255,
        (color >> 16) & 255,
        (color >> 24) & 255
        );
    }

    /**
     * Decode a hex string into a {@code BColor}
     *
     * @param rrggbb the hex string
     *
     * @return a new {@code BColor}
     */
    public static BColor ofHex(String rrggbb) {
        return ofColorRGB(Integer.decode("0x" + rrggbb));
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }

    public int getAlpha() {
        return a;
    }

    public int getColor() {
        return r + g << 8 + b << 16 + a << 24;
    }

    public String toString() {
        return "(" + r + ", " + g + ", " + b + ", " + a + ")";
    }
}
