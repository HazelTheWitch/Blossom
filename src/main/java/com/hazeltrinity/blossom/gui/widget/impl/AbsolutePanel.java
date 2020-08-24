package com.hazeltrinity.blossom.gui.widget.impl;

import com.hazeltrinity.blossom.gui.widget.BPanelWidget;
import com.hazeltrinity.blossom.gui.widget.BWidget;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Organizes widgets by their absolute position on the parent, either by % or
 * constant offset.
 */
public class AbsolutePanel extends BPanelWidget {
    protected List<WidgetLocation> children = new ArrayList<WidgetLocation>();

    public AbsolutePanel addChild(BWidget widget, double ax, double bx, double ay, double by) {
        children.add(new WidgetLocation(this, widget, ax, bx, ay, by));
        return this;
    }

    @Override
    public @Nullable List<ChildWidget> getChildren() {
        List<ChildWidget> children = new ArrayList<ChildWidget>(this.children.size());
        for (WidgetLocation widgetLocation : this.children) {
            children.add(widgetLocation.getChild());
        }
        return children;
    }

    @Override
    public Size getMinimumSize() {
        Size max = new Size(0, 0);

        for (WidgetLocation location : children) {
            max = max.max(location.getRequiredSize());
        }

        return max.max(super.getMinimumSize());
    }

    protected static class WidgetLocation {
        public final AbsolutePanel parent;

        public final BWidget widget;

        public final double ax, bx, ay, by;

        public WidgetLocation(AbsolutePanel parent, BWidget widget, double ax, double bx, double ay, double by) {
            this.parent = parent;
            this.widget = widget;
            this.ax = ax;
            this.bx = bx;
            this.ay = ay;
            this.by = by;
        }

        public ChildWidget getChild() {
            return new ChildWidget(
                    widget,
                    (int) (ax * parent.getWidth() + bx) + parent.leftMargin,
                    (int) (ay * parent.getHeight() + by) + parent.topMargin
            );
        }

        public Size getRequiredSize() {
            return new Size(
                    ceil((bx + widget.getWidth()) / (1 - ax)) + parent.leftMargin + parent.rightMargin,
                    ceil((by + widget.getHeight()) / (1 - ay)) + parent.topMargin + parent.bottomMargin
            );
        }

        private int ceil(double x) {
            return (int) Math.ceil(x);
        }
    }
}