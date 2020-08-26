package com.hazeltrinity.blossom.gui.drawing;

import com.hazeltrinity.blossom.gui.BColor;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A background painter that looks like vanilla GUI
 */
public class VanillaBackgroundPainter extends BackgroundPainter {
    public final BColor shadow, panel, hilight, outline;

    /**
     * Create a vanilla looking background painter
     */
    public VanillaBackgroundPainter() {
        this(BDrawing.GUI_SHADOW, BDrawing.GUI_PANEL, BDrawing.GUI_HILIGHT);
    }

    /**
     * Create a background painter with a defined shadow, panel, and hilight color. See {@link
     * BDrawing#drawGuiPanel(MatrixStack, int, int, int, int, BColor, BColor, BColor, BColor)} for description of
     * parameters.
     *
     * @param shadow  the shadow color
     * @param panel   the main panel color
     * @param hilight the hilight color
     */
    public VanillaBackgroundPainter(BColor shadow, BColor panel, BColor hilight) {
        this(shadow, panel, hilight, BColor.BLACK);
    }

    /**
     * Creates a background painter with all colors defined. See {@link BDrawing#drawGuiPanel(MatrixStack, int, int,
     * int, int, BColor, BColor, BColor, BColor)} for description of parameters.
     *
     * @param shadow  the shadow color
     * @param panel   the panel color
     * @param hilight the hilight color
     * @param outline the outline color
     */
    public VanillaBackgroundPainter(BColor shadow, BColor panel, BColor hilight, BColor outline) {
        this.shadow = shadow;
        this.panel = panel;
        this.hilight = hilight;
        this.outline = outline;
    }

    @Override
    public void paintBackgroundInner(MatrixStack matrices, int x, int y, int width, int height) {
        paintBackground(matrices, x - 3, y - 3, width + 6, height + 6);
    }

    @Override
    public void paintBackground(MatrixStack matrices, int x, int y, int width, int height) {
        BDrawing.drawGuiPanel(matrices, x, y, width, height, shadow, panel, hilight, outline);
    }

}