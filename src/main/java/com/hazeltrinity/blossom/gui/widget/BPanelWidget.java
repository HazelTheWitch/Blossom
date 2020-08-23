package com.hazeltrinity.blossom.gui.widget;

/**
 * An organizational widget with margins. <b>Does not handle the math behind the margins, subclasses are expected to manage that.</b>
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


}