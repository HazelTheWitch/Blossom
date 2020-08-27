package com.hazeltrinity.blossom.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

/**
 * An organizational widget with margins. <b>Does not handle the math behind the margins, subclasses are expected to
 * manage that.</b>
 */
public abstract class BPanelWidget extends BNamedWidget implements Parent {

    protected int leftMargin, topMargin, rightMargin, bottomMargin;

    /**
     * Sets a constant offset from each edge of the widget
     *
     * @param margin the margin to apply to each edge
     *
     * @return this widget for chaining
     */
    public BPanelWidget setMargins(int margin) {
        return setMargins(margin, margin, margin, margin);
    }

    /**
     * Sets the margins to be equal horizontally and equal vertically.
     *
     * @param horizontal the margin to apply to the left and right
     * @param vertical   the margin to apply to the top and bottom
     *
     * @return this widget for chaining
     */
    public BPanelWidget setMargins(int horizontal, int vertical) {
        return setMargins(horizontal, vertical, horizontal, vertical);
    }

    /**
     * Set the margins individually for each side.
     *
     * @param left   the left margin
     * @param top    the top margin
     * @param right  the right margin
     * @param bottom the bottom margin
     *
     * @return this widget for chaining
     */
    public BPanelWidget setMargins(int left, int top, int right, int bottom) {
        this.leftMargin = left;
        this.topMargin = top;
        this.rightMargin = right;
        this.bottomMargin = bottom;
        return this;
    }

    /**
     * Add this widget's margins to the inner size
     *
     * @param oldSize the inner size of the panel
     *
     * @return the outer size
     */
    protected Size addMargins(Size oldSize) {
        return new Size(oldSize.width + leftMargin + rightMargin, oldSize.height + topMargin + bottomMargin);
    }

    @Override
    public void tick() {
        super.tick();

        List<ChildWidget> children = getChildren();

        if (children != null) {
            for (ChildWidget child : children) {
                child.widget.tick();
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void paint(MatrixStack matrices, int mouseX, int mouseY) {
        super.paint(matrices, mouseX, mouseY);

        List<ChildWidget> children = getChildren();

        if (children != null) {
            for (ChildWidget child : children) {
                matrices.push();
                matrices.translate(child.x, child.y, 0);
                child.widget.paint(matrices, mouseX - child.x, mouseY - child.y);
                matrices.pop();
            }
        }
    }
}