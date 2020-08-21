package com.hazeltrinity.hazellib.gui.widget;

import java.util.List;

import com.hazeltrinity.hazellib.gui.drawing.BackgroundPainter;
import com.hazeltrinity.hazellib.gui.widget.scaling.ProportionalScaler;
import com.hazeltrinity.hazellib.gui.widget.scaling.Scaler;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.util.math.MatrixStack;

public abstract class HWidget {
    protected BackgroundPainter backgroundPainter = null;

    private Scaler scaler = new ProportionalScaler(0.5, 0.5);

    /**
     * Paint this widget
     * 
     * @param matrices rendering matrices
     * @param x        the x coordinate of the left edge of the widget
     * @param y        the y coordinate of the left edge of the widget
     * @param mouseX   the x coordinate of the mouse relative to the widget
     * @param mouseY   the y coordinate of the mouse relative to the widget
     */
    public abstract void paint(MatrixStack matrices, int x, int y, int width, int height, int mouseX, int mouseY);

    /**
     * Paint this widget and its children based on the specified container size.
     * 
     * Attempts to paint the background of the widget before the actual widget.
     * 
     * @param matrices the current matrix stack
     * @param x        the x coordinate of the left edge of the widget
     * @param y        the y coordinate of the top edge of the widget
     * @param width    the width of the widget
     * @param height   the height of the widget
     * @param mouseX   the x coordinate of the mouse relative to the widget
     * @param mouseY   the y coordinate of the mouse relative to the widget
     */
    public void paintWithChildren(MatrixStack matrices, int x, int y, int width, int height, int mouseX, int mouseY) {
        int wWidth = scaler.getWidth(width, height);
        int wHeight = scaler.getHeight(width, height);

        if (backgroundPainter != null) {
            backgroundPainter.paintBackgroundInner(x, y, wWidth, wHeight);
        }

        paint(matrices, x, y, wWidth, wHeight, mouseX, mouseY);

        List<ChildWidget> children = getChildren(wWidth, wHeight);

        if (children != null) {
            for (ChildWidget child : children) {
                child.widget.paintWithChildren(matrices, x + child.x, y + child.y, wWidth, wHeight, mouseX - child.x, mouseY - child.y);
            }
        }
    }

    public void tick() {

    }

    /**
     * Get all children of this widget. Does not include the children of children or
     * any deeper.
     * 
     * @return a list of all children of this widget
     */
    @Nullable
    public List<ChildWidget> getChildren(int width, int height) {
        return null;
    }

    public HWidget setBackgroundPainter(BackgroundPainter painter) {
        this.backgroundPainter = painter;
        return this;
    }

    public Scaler getScaler() {
        return scaler;
    }

    public HWidget setScaler(Scaler scaler) {
        this.scaler = scaler;
        return this;
    }

    /**
     * Stores the location and width of a child widget.
     * <p>
     * <i>{@code x} and {@code y} are relative to t his widget.</i>
     * </p>
     */
    protected class ChildWidget {
        public final int x, y;
        public final HWidget widget;

        public ChildWidget(int x, int y, HWidget widget) {
            this.x = x;
            this.y = y;

            this.widget = widget;
        }
    }
}