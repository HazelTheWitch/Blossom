package com.hazeltrinity.blossom.gui.drawing;

public class VanillaBackgroundPainter extends BackgroundPainter {
    public final int shadow, panel, hilight, outline;

    public VanillaBackgroundPainter() {
        this(0xFF555555, 0xFFC6C6C6);
    }

    public VanillaBackgroundPainter(int shadow, int panel) {
        this(shadow, panel, 0xFFFFFFFF, 0xFF000000);
    }

    public VanillaBackgroundPainter(int shadow, int panel, int hilight, int outline) {
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