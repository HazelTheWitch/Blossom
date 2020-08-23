package com.hazeltrinity.blossom.gui.widget.impl;

import com.hazeltrinity.blossom.gui.drawing.BDrawing;
import com.hazeltrinity.blossom.gui.widget.BWidget;
import net.minecraft.client.util.math.MatrixStack;

public class WRect extends BWidget {

    private int color = 0x00_000000;

    public WRect setColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        super.paint(matrices, x, y, mouseX, mouseY);
        BDrawing.drawRect(x, y, getWidth(), getHeight(), color);
    }
}
