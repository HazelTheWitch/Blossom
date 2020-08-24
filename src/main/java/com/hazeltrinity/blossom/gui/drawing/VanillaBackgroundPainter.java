package com.hazeltrinity.blossom.gui.drawing;

import com.hazeltrinity.blossom.gui.BColor;

public class VanillaBackgroundPainter extends BackgroundPainter {

    public final BColor shadow, panel, hilight, outline;

    public VanillaBackgroundPainter() {
        this(BDrawing.GUI_SHADOW, BDrawing.GUI_PANEL);
    }

    public VanillaBackgroundPainter(BColor shadow, BColor panel) {
        this(shadow, panel, BColor.WHITE, BColor.BLACK);
    }

    public VanillaBackgroundPainter(BColor shadow, BColor panel, BColor hilight, BColor outline) {
        this.shadow = shadow;
        this.panel = panel;
        this.hilight = hilight;
        this.outline = outline;
    }

    @Override
    public void paintBackgroundInner(int x, int y, int width, int height) {
        paintBackground(x - 3, y - 3, width + 6, height + 6);
    }

    @Override
    public void paintBackground(int x, int y, int width, int height) {
        BDrawing.drawGuiPanel(x, y, width, height, shadow, panel, hilight, outline);
    }

}