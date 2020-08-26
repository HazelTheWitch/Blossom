package com.hazeltrinity.blossom.gui.widget;

import com.hazeltrinity.blossom.gui.BColor;
import com.hazeltrinity.blossom.gui.drawing.BDrawing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

/**
 * A widget which has a name.
 */
public abstract class BNamedWidget extends BWidget {

    private boolean drawTitle = true;

    protected Text name = new LiteralText("");

    protected double widgetAlignment = 0.5;
    protected double textAlignment = 0.5;

    protected BColor color = BColor.ofColorRGB(0x404040);

    private double clamp01(double x) {
        if (x < 0) {
            return 0;
        } else if (x > 1) {
            return 1;
        } else {
            return x;
        }
    }

    /**
     * Set the color of the name.
     *
     * @param color the color to use
     *
     * @return this widget for chaining
     */
    public BNamedWidget setNameColor(BColor color) {
        this.color = color;
        return this;
    }

    /**
     * Set the text and widget alignment of this widget.
     *
     * @param widgetAlignment the alignment within the container widget
     * @param textAlignment   the alignment for the text, as seen here Drawing.drawString
     *
     * @return this widget for chaining
     */
    public BNamedWidget setAlignment(double widgetAlignment, double textAlignment) {
        this.widgetAlignment = clamp01(widgetAlignment);
        this.textAlignment = clamp01(textAlignment);
        return this;

    }

    /**
     * Set the name of this widget.
     *
     * @param name a string converted to a {@code LiteralText}
     *
     * @return this widget for chaining
     */
    public BNamedWidget setName(String name) {
        this.name = new LiteralText(name);
        return this;
    }

    /**
     * Set the name of this widget.
     *
     * @param name the name of the widget
     *
     * @return this widget for chaining
     */
    public BNamedWidget setName(Text name) {
        this.name = name;
        return this;
    }

    /**
     * Determines whether to draw the title of this widget.
     * <p>
     * False should effectively "turn off" the BNamedWidget subclass.
     *
     * @param drawTitle whether to draw the title
     *
     * @return this widget for chaining
     */
    public BNamedWidget doTitle(boolean drawTitle) {
        this.drawTitle = drawTitle;
        return this;
    }

    /**
     * Get this widget's name.
     *
     * @return the name
     */
    public Text getName() {
        return name;
    }

    @Override
    public void paint(MatrixStack matrices, int mouseX, int mouseY) {
        super.paint(matrices, mouseX, mouseY);

        if (drawTitle) {
            BDrawing.drawString(matrices, name, (int) (getWidth() * widgetAlignment), 4, textAlignment, color);
        }
    }

    @Override
    public Size getMinimumSize() {
        Size size = new Size(0, 0);
        if (drawTitle) {
            MinecraftClient client = MinecraftClient.getInstance();
            int width = client.textRenderer.getWidth(name);

            if (Math.abs(widgetAlignment - textAlignment) < 1e-5) {
                size = new Size(width, 7);
            } else if (widgetAlignment < 1e-5) {
                size = new Size((int) (width * textAlignment / 1e-5), 7);
            } else {
                size = new Size((int) (width * textAlignment / widgetAlignment), 7);
            }
        }

        return size.max(super.getMinimumSize());
    }
}