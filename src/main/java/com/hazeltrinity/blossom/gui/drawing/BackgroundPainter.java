package com.hazeltrinity.blossom.gui.drawing;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;

/**
 * Draws the backgrounds of widgets.
 */
@Environment(EnvType.CLIENT)
public abstract class BackgroundPainter {

    /**
     * Draws a background excluding border.
     *
     * @param matrices the MatrixStack to use for drawing
     * @param x        the inner top left corner x coordinate
     * @param y        the inner top right corner y coordinate
     * @param width    the inner width of the background
     * @param height   the inner height of the background
     */
    public void paintBackgroundInner(MatrixStack matrices, int x, int y, int width, int height) {
        paintBackground(matrices, x, y, width, height);
    }

    /**
     * Draws a background including border.
     *
     * @param matrices the MatrixStack to use for drawing
     * @param x        the outer top left corner x coordinate
     * @param y        the outer top right corner y coordinate
     * @param width    the outer width of the background
     * @param height   the outer height of the background
     */
    public abstract void paintBackground(MatrixStack matrices, int x, int y, int width, int height);
}