package com.hazeltrinity.blossom.gui.widget.impl;

import com.hazeltrinity.blossom.gui.BColor;
import com.hazeltrinity.blossom.gui.drawing.BDrawing;
import com.hazeltrinity.blossom.gui.widget.BWidget;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A basic flat rectangle widget, useful for debugging.
 */
public class WRect extends BWidget {
    private BColor color = BColor.CLEAR;

    /**
     * Set the color of the rectangle.
     *
     * @param color the new color
     *
     * @return this WRect for chaining
     */
    public WRect setColor(BColor color) {
        this.color = color;
        return this;
    }

    @Override
    public void paint(MatrixStack matrices, int mouseX, int mouseY) {
        super.paint(matrices, mouseX, mouseY);
        BDrawing.drawRect(matrices, 0, 0, getWidth(), getHeight(), color);
    }
}
