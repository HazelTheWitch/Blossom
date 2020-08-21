package com.hazeltrinity.hazellib.gui.widget.scaling;

public class ProportionalScaler implements Scaler{
    public final double wScale, hScale;

    public ProportionalScaler(double wScale, double hScale) {
        this.wScale = wScale;
        this.hScale = hScale;
    }

    @Override
    public int getWidth(int width, int height) {
        return (int)(width*wScale);
    }

    @Override
    public int getHeight(int width, int height) {
        return (int)(height*hScale);
    }
}