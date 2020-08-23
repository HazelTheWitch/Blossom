package com.hazeltrinity.blossom.gui.widget;

import java.util.List;

import com.hazeltrinity.blossom.gui.drawing.BackgroundPainter;

import net.minecraft.client.util.math.MatrixStack;

public abstract class BWidget {
    protected BackgroundPainter backgroundPainter = null;

    protected Size minimumSize;

    public BWidget() {
        this(new Size(0, 0));
    }

    public BWidget(Size minimumSize) {
        this.minimumSize = minimumSize;
    }

    /**
     * Get the minimum size this panel can be.
     * 
     * <p>
     * <i>The default implementation returns the minumum size set with
     * {@link #setMinimumSize}. That behaviour should be kept intact with implementations</i>
     * </p>
     * 
     * @return the minimum size
     */
    public Size getMinimumSize() {
        return minimumSize;
    }

    /**
     * Gets a size where each dimension is the minimum of the max size and size
     * hint.
     * 
     * @return the size
     */
    public Size getSize() {
        if (this instanceof Panel) {
            return getMinimumSize().max(((Panel) this).getSizeHint());
        }

        return getMinimumSize();
    }

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
        if (backgroundPainter != null) {
            backgroundPainter.paintBackgroundInner(x, y, width, height);
        }

        paint(matrices, x, y, width, height, mouseX, mouseY);

        if (this instanceof Panel) {
            List<ChildWidget> children = ((Panel) this).getChildren(width, height);

            if (children != null) {
                for (ChildWidget child : children) {
                    child.widget.paintWithChildren(matrices, x + child.x, y + child.y, child.width, child.height, mouseX - child.x, mouseY - child.y);
                }
            }
        }
    }

    public void tick(int width, int height) {

    }

    public void tickWithChildren(int width, int height) {
        tick(width, height);

        if (this instanceof Panel) {
            List<ChildWidget> children = ((Panel) this).getChildren(width, height);

            if (children != null) {
                for (ChildWidget child : children) {
                    child.widget.tickWithChildren(child.width, child.height);
                }
            }
        }
    }

    public BWidget setBackgroundPainter(BackgroundPainter painter) {
        this.backgroundPainter = painter;
        return this;
    }

    /**
     * Stores the location and width of a child widget.
     * <p>
     * <i>{@code x} and {@code y} are relative to t his widget.</i>
     * </p>
     */
    public static class ChildWidget {
        public final int x, y;
        public final int width, height;
        public final BWidget widget;

        public ChildWidget(BWidget widget, int x, int y) {
            this(widget, x, y, widget.getSize());
        }

        public ChildWidget(BWidget widget, int x, int y, Size size) {
            this(widget, x, y, size.width, size.height);
        }

        public ChildWidget(BWidget widget, int x, int y, int width, int height) {
            this.x = x;
            this.y = y;

            this.width = width;
            this.height = height;

            this.widget = widget;
        }
    }

    /**
     * Stores a size, (width, height).
     */
    public static class Size {
        public final int width, height;

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * Get the size with maximum in each direction.
         * 
         * @param other the other size to compare to
         * @return the size with maximum length in each direction
         */
        public Size max(Size other) {
            return new Size(Math.max(width, other.width), Math.max(height, other.height));
        }
    }
}