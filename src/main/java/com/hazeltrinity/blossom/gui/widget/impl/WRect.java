package com.hazeltrinity.blossom.gui.widget.impl;

import com.hazeltrinity.blossom.gui.BColor;
import com.hazeltrinity.blossom.gui.drawing.BDrawing;
import com.hazeltrinity.blossom.gui.widget.BWidget;
import net.minecraft.client.util.math.MatrixStack;

public class WRect extends BWidget {
    private BColor color = BColor.CLEAR;

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
