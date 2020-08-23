package com.hazeltrinity.blossom.gui.widget;

import com.hazeltrinity.blossom.gui.drawing.BDrawing;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public abstract class BNamedWidget extends BWidget {
    private boolean drawTitle = true;

    protected Text name;

    protected double widgetAlignment;
    protected double textAlignment;

    protected int color;

    // TODO: convert to fluent builder style initialization flow to match

    /**
     * Creates a new HNamedWidget centered in the widget, looks vanilla.
     * 
     * @param name the title of the widget
     */
    public BNamedWidget(Text name) {
        this(name, 0.5, 0.5, 0x404040);
    }

    /**
     * Creates a new HNamedWidget centered in the widget, looks vanilla.
     * 
     * @param name the title of the widget
     */
    public BNamedWidget(Size minimumSize) {
        this(minimumSize, new LiteralText(""), 0.5, 0.5, 0x404040);
    }

    /**
     * Creates a new HNamedWidget centered in the widget, looks vanilla.
     * 
     * @param minimumSize the minimum size of the widget
     * @param name        the title of the widget
     */
    public BNamedWidget(Size minimumSize, Text name) {
        this(minimumSize, name, 0.5, 0.5, 0x404040);
    }

    /**
     * Create a new HNamedWidget with minimum size 0, 0.
     * 
     * @param name            the title of the widget
     * @param widgetAlignment the alignment within the widget
     * @param textAlignment   the alignment of the text
     * @param color           the color to draw the title
     */
    public BNamedWidget(Text name, double widgetAlignment, double textAlignment, int color) {
        this(new Size(0, 0), name, widgetAlignment, textAlignment, color);
    }

    /**
     * Create a new HNamedWidget
     * 
     * @param minimumSize     the minimum size of the widget
     * @param name            the title of the widget
     * @param widgetAlignment the alignment within the widget
     * @param textAlignment   the alignment of the text
     * @param color           the color to draw the title
     */
    public BNamedWidget(Size minimumSize, Text name, double widgetAlignment, double textAlignment, int color) {
        super(minimumSize);
        this.name = name;
        this.widgetAlignment = clamp01(widgetAlignment);
        this.textAlignment = clamp01(textAlignment);

        this.color = color;
    }

    private double clamp01(double x) {
        if (x < 0) {
            return 0;
        } else if (x > 1) {
            return 1;
        } else {
            return x;
        }
    }

    public Text getName() {
        return name;
    }

    public void paint(MatrixStack matrices, int x, int y, int width, int height, int mouseX, int mouseY) {
        if (drawTitle) {
            BDrawing.drawString(matrices, name, (int)(x + width * widgetAlignment), y+4, textAlignment, color);
        }
    }

    /**
     * Determines whether to draw the title of this widget.
     * 
     * False should effectively "turn off" the HNamedWidget subclass.
     * 
     * @param drawTitle whether to draw the title
     * @return this widget for chaining
     */
    public BNamedWidget doTitle(boolean drawTitle) {
        this.drawTitle = drawTitle;
        return this;
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
                size = new Size((int)(width * textAlignment / 1e-5), 7);
            } else {
                size = new Size((int)(width * textAlignment / widgetAlignment), 7);
            }
        }

        return size.max(super.getMinimumSize());
    }
}