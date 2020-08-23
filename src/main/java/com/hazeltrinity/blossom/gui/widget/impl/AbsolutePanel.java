package com.hazeltrinity.blossom.gui.widget.impl;

import java.util.ArrayList;
import java.util.List;

import com.hazeltrinity.blossom.gui.widget.BNamedWidget;
import com.hazeltrinity.blossom.gui.widget.BWidget;
import com.hazeltrinity.blossom.gui.widget.Panel;

import org.jetbrains.annotations.Nullable;

/**
 * Organizes widgets by their absolute position on the panel, either by % or
 * constant offset.
 */
public class AbsolutePanel extends BNamedWidget implements Panel {
    protected List<WidgetLocation> children = new ArrayList<WidgetLocation>();

    public AbsolutePanel addChild(BWidget widget, double ax, double bx, double ay, double by) {
        children.add(new WidgetLocation(widget, ax, bx, ay, by));
        return this;
    }

    @Override
    public @Nullable List<ChildWidget> getChildren(int width, int height) {
        List<ChildWidget> children = new ArrayList<ChildWidget>(this.children.size());
        for (WidgetLocation widgetLocation : this.children) {
            children.add(widgetLocation.getChild(width, height));
        }
        return children;
    }

    @Override
    public Size getSizeHint() {
        Size max = new Size(0, 0);

        for (WidgetLocation location : children) {
            max = max.max(location.getRequiredSize());
        }

        return max;
    }
    
    protected static class WidgetLocation {
        public final BWidget widget;

        public final double ax, bx, ay, by;

        public WidgetLocation(BWidget widget, double ax, double bx, double ay, double by) {
            this.widget = widget;
            this.ax = ax;
            this.bx = bx;
            this.ay = ay;
            this.by = by;
        }

        public ChildWidget getChild(int width, int height) {
            return new ChildWidget(
                widget,
                (int)(ax * width + bx),
                (int)(ay * height + by)
            );
        }

        public Size getRequiredSize() {
            Size widgetSize = widget.getSize();
            return new Size(
                ceil((bx + widgetSize.width) / (1 - ax)),
                ceil((by + widgetSize.height) / (1 - ay))
            );
        }

        private int ceil(double x) {
            return (int) Math.ceil(x);
        }
    }
}