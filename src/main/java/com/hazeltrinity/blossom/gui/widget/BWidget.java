package com.hazeltrinity.blossom.gui.widget;

import java.util.ArrayList;
import java.util.List;

import com.hazeltrinity.blossom.gui.drawing.BackgroundPainter;

import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.util.math.MatrixStack;

public abstract class BWidget {
    protected BackgroundPainter backgroundPainter = null;

    protected Size minimumSize = new Size(0, 0);

    private Size cachedSize = null;
    
    public BWidget setBackgroundPainter(BackgroundPainter painter) {
        this.backgroundPainter = painter;
        return this;
    }

    public BWidget setMinimumSize(Size minimumSize) {
        this.minimumSize = minimumSize;
        return this;
    }

    /**
     * Get the minimum size this widget can be.
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
     * Calculates a size where each dimension is the minimum of the max size and size
     * hint.
     * 
     * <p>
     * <i>Should almost never be used.</i>
     * </p>
     * 
     * @return the size
     */
    public Size calculateSize() {
        if (this instanceof Parent) {
            return getMinimumSize().max(((Parent) this).getSizeHint());
        }

        return getMinimumSize();
    }

    /**
     * Gets a cached version of this widget's size, to avoid calculating it many times per paint. This cache will be invalidated at the beginning of each paint event.
     * 
     * @return the cached size
     */
    public Size getSize() {
        if (cachedSize == null) {
            cachedSize = calculateSize();
        }

        return cachedSize;
    }

    public int getWidth() {
        return getSize().width;
    }

    public int getHeight() {
        return getSize().height;
    }

    /**
     * Invalidate the size cache and that of all ancestors
     */
    public void invalidateCachedSize() {
        for (BWidget widget : getAncestors()) {
            widget.cachedSize = null;
        }
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
    public abstract void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY);

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
    public void paintWithChildren(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        if (backgroundPainter != null) {
            backgroundPainter.paintBackgroundInner(x, y, getWidth(), getHeight());
        }

        paint(matrices, x, y, mouseX, mouseY);

        if (this instanceof Parent) {
            List<ChildWidget> children = ((Parent) this).getChildren();

            if (children != null) {
                for (ChildWidget child : children) {
                    child.widget.paintWithChildren(matrices, x + child.x, y + child.y, mouseX - child.x, mouseY - child.y);
                }
            }
        }
    }

    @OverrideOnly
    public void tick() { }

    public void tickWithChildren() {
        tick();

        if (this instanceof Parent) {
            List<ChildWidget> children = ((Parent) this).getChildren();

            if (children != null) {
                for (ChildWidget child : children) {
                    child.widget.tickWithChildren();
                }
            }
        }
    }

    /**
     * Return the topmost widget at {@code mouseX}, {@code mouseY} relative to the
     * top left corner of the widget.
     * 
     * <p>
     * Returns null if the given mouse coordinates do not lie within the widget
     * </p>
     * 
     * @param width  the width of the widget
     * @param height the height of the widget
     * @param mouseX the x position of the mouse
     * @param mouseY the y position of the mouse
     * @return the topmost widget
     */
    @Nullable
    public BWidget hit(int mouseX, int mouseY) {
        // Mouse does not lie in this widget
        if (mouseX < 0 || mouseY < 0 || mouseX >= getWidth() || mouseY >= getHeight()) {
            return null;
        }

        if (this instanceof Parent) {
            List<ChildWidget> children = ((Parent) this).getChildren();

            if (children != null) {
                // Top to bottom
                for (int i = children.size() - 1; i >= 0; i--) {
                    ChildWidget child = children.get(i);

                    BWidget result = child.widget.hit(mouseX - child.x, mouseY - child.y);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        return this;
    }

    /**
     * Get a list of all widgets descending from this widget including this widget
     * 
     * @return a list of widgets
     */
    public List<BWidget> getAncestors() {
        List<BWidget> ancestors = new ArrayList<BWidget>();
        ancestors.add(this);

        if (this instanceof Parent) {
            List<ChildWidget> children = ((Parent) this).getChildren();

            if (children != null) {
                for (ChildWidget child : children) {
                    ancestors.addAll(child.widget.getAncestors());
                }
            }
        }

        return ancestors;
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
     * Stores a size: (width, height).
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