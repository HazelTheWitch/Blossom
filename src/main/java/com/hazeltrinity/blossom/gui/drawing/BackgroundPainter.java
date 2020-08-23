package com.hazeltrinity.blossom.gui.drawing;

/**
 * Draws the backgrounds of widgets.
 */
public abstract class BackgroundPainter {
    /**
     * Draws a background excluding border.
     * 
     * @param x      the inner top left corner x coordinate
     * @param y      the inner top right corner y coordinate
     * @param width  the inner width of the background
     * @param height the inner height of the background
     */
    public void paintBackgroundInner(int x, int y, int width, int height) {
        paintBackground(x, y, width, height);
    }

    /**
     * Draws a background including border.
     * 
     * @param x      the outer top left corner x coordinate
     * @param y      the outer top right corner y coordinate
     * @param width  the outer width of the background
     * @param height the outer height of the background
     */
    public abstract void paintBackground(int x, int y, int width, int height);
}