package com.hazeltrinity.hazellib.gui.widget.scaling;

/**
 * Gives a constant width and height.
 */
public class ConstantScaler implements Scaler {
    public final int width, height;

    public ConstantScaler(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth(int width, int height) {
        return this.width;
    }

    @Override
    public int getHeight(int width, int height) {
        return this.height;
    }    
}