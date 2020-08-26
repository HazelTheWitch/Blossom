package com.hazeltrinity.blossom.gui.widget;

import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

/**
 * An organizational widget with margins. <b>Does not handle the math behind the margins, subclasses are expected to
 * manage that.</b>
 */
public abstract class BPanelWidget extends BNamedWidget implements Parent {

    protected int leftMargin, topMargin, rightMargin, bottomMargin;

    public BPanelWidget setMargins(int margin) {
        return setMargins(margin, margin, margin, margin);
    }

    public BPanelWidget setMargins(int horizontal, int vertical) {
        return setMargins(horizontal, vertical, horizontal, vertical);
    }

    public BPanelWidget setMargins(int left, int top, int right, int bottom) {
        this.leftMargin = left;
        this.topMargin = top;
        this.rightMargin = right;
        this.bottomMargin = bottom;
        return this;
    }

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