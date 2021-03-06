package com.hazeltrinity.blossom.gui.widget;

import com.hazeltrinity.blossom.gui.drawing.BackgroundPainter;
import com.hazeltrinity.blossom.gui.screen.BDescription;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a widget in a GUI, they are organized in a tree structure.
 */
public abstract class BWidget {
    @Environment(EnvType.CLIENT)
    protected BackgroundPainter backgroundPainter = null;

    protected Size minimumSize = new Size(0, 0);
    protected boolean focusable = false;
    protected BDescription description;
    private Size cachedSize = null;
    private boolean focussed = false;

    /**
     * Set this widget to a new description
     *
     * @param description the new description
     */
    public void setDescription(BDescription description) {
        this.description = description;
    }

    /**
     * Called when this widget gains focus.
     */
    @OverrideOnly
    public void onFocus() {
    }

    /**
     * Called when this widget loses focus.
     */
    @OverrideOnly
    public void onLoseFocus() {
    }

    /**
     * Called once every frame.
     */
    @OverrideOnly
    public void tick() {
    }

    /**
     * Focus this widget
     */
    public void focus() {
        focussed = true;
        onFocus();
    }

    /**
     * Unfocus this widget
     */
    public void unfocus() {
        focussed = false;
        onLoseFocus();
    }

    /**
     * Get if this Widget is focus.
     *
     * @return if this widget is focus
     */
    public boolean isFocussed() {
        return focussed;
    }

    /**
     * Set the background painter
     *
     * @param painter the new background painter
     *
     * @return this widget for chaining
     */
    @Environment(EnvType.CLIENT)
    public BWidget setBackgroundPainter(BackgroundPainter painter) {
        this.backgroundPainter = painter;
        return this;
    }

    /**
     * Can this widget take focus?
     *
     * @return whether or not this widget can be focussed
     */
    public boolean isFocusable() {
        return focusable;
    }

    /**
     * Set whether or not this widget is focusable, useful for buttons, scrolling, etc.
     *
     * @param focusable is this widget focusable
     *
     * @return this widget for chaining
     */
    public BWidget setFocusable(boolean focusable) {
        this.focusable = focusable;
        return this;
    }

    /**
     * Set the absolute minimum size of this widget
     *
     * @param width  the width of the widget
     * @param height the height of the widget
     *
     * @return the widget for chaining
     */
    public BWidget setMinimumSize(int width, int height) {
        return setMinimumSize(new Size(width, height));
    }

    /**
     * Get the minimum size this widget can be.
     *
     * <p>
     * <i>The default implementation returns the minumum size set with
     * {@link #setMinimumSize}. That behaviour should be kept intact with implementations. Any implimentation can follow
     * this by adding {@code super.getMinimumSize().max(<return value>)}.</i>
     * </p>
     *
     * @return the minimum size
     */
    public Size getMinimumSize() {
        return minimumSize;
    }

    /**
     * Set the minimum size of this widget.
     *
     * @param minimumSize the minimum size
     *
     * @return this widget for chaining
     */
    public BWidget setMinimumSize(Size minimumSize) {
        this.minimumSize = minimumSize;
        return this;
    }

    /**
     * Calculates a size where each dimension is the minimum of the max size and size hint.
     *
     * <p>
     * <i>Should almost never be used.</i>
     * </p>
     *
     * @return the size
     */
    public Size calculateSize() {
        return getMinimumSize();
    }

    /**
     * Gets a cached version of this widget's size, to avoid calculating it many times per paint. This cache will be
     * invalidated at the beginning of each paint event.
     *
     * @return the cached size
     */
    public Size getSize() {
        if (cachedSize == null) {
            cachedSize = calculateSize();
        }

        return cachedSize;
    }

    /**
     * Get the width of the widget
     *
     * @return the width
     */
    public int getWidth() {
        return getSize().width;
    }

    /**
     * Get the height of the widget
     *
     * @return the height
     */
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
     * @param mouseX   the x coordinate of the mouse relative to the widget
     * @param mouseY   the y coordinate of the mouse relative to the widget
     */
    @Environment(EnvType.CLIENT)
    @OverrideOnly
    public void paint(MatrixStack matrices, int mouseX, int mouseY) {
        if (backgroundPainter != null) {
            backgroundPainter.paintBackgroundInner(matrices, 0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Return the topmost widget at {@code mouseX}, {@code mouseY} relative to the top left corner of the widget.
     *
     * <p>
     * Returns null if the given mouse coordinates do not lie within the widget
     * </p>
     *
     * @param mouseX the x position of the mouse
     * @param mouseY the y position of the mouse
     *
     * @return the topmost widget
     */
    @Nullable
    public HitResult hit(int mouseX, int mouseY) {
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

                    HitResult result = child.widget.hit(mouseX - child.x, mouseY - child.y);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        return new HitResult(this, mouseX, mouseY);
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

        /**
         * Create a new child widget at a position, it calculates the size
         *
         * @param widget the widget to place
         * @param x      the x position
         * @param y      the y position
         */
        public ChildWidget(BWidget widget, int x, int y) {
            this.x = x;
            this.y = y;

            this.width = widget.getWidth();
            this.height = widget.getHeight();

            this.widget = widget;
        }
    }

    /**
     * Stores a size: (width, height).
     */
    public static class Size {

        public final int width, height;

        /**
         * Create a new size with a given width and height
         *
         * @param width  the width
         * @param height the height
         */
        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * Get the size with maximum in each direction.
         *
         * @param other the other size to compare to
         *
         * @return the size with maximum length in each direction
         */
        public Size max(Size other) {
            return new Size(Math.max(width, other.width), Math.max(height, other.height));
        }
    }

    /**
     * The result of a call to {@link BWidget#hit(int, int)}
     */
    public static class HitResult {

        public final BWidget widget;
        public final int mouseX, mouseY;

        /**
         * Create a new HitResult at a given mouse x and y
         *
         * @param widget the widget to hit
         * @param mouseX the x position of the mouse relative to the widget
         * @param mouseY the y position of the mouse relative to the widget
         */
        protected HitResult(BWidget widget, int mouseX, int mouseY) {
            this.widget = widget;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }
    }
}