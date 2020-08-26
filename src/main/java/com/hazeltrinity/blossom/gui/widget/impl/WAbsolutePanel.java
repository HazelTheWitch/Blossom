package com.hazeltrinity.blossom.gui.widget.impl;

import com.hazeltrinity.blossom.gui.widget.BPanelWidget;
import com.hazeltrinity.blossom.gui.widget.BWidget;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Organizes widgets by their absolute position on the parent, either by % or constant offset or both.
 */
public class WAbsolutePanel extends BPanelWidget {
    protected List<WidgetLocation> children = new ArrayList<WidgetLocation>();

    /**
     * Add a child to the panel.
     * <p>
     * The placement is defined by the top left corner's position, where:
     * <p>
     * x = ax*width+bx;
     * </p>
     * and
     * <p>
     * y=ay*height+by
     * </p>
     * </p>
     *
     * @param widget the widget to add
     * @param ax     the a value of the x equation
     * @param bx     the b value of the x equation
     * @param ay     the a value of the y equation
     * @param by     the b value of the y equation
     *
     * @return the panel for chaining
     */
    public WAbsolutePanel addChild(BWidget widget, double ax, double bx, double ay, double by) {
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

    /**
     * Represents a widget location on the panel
     */
    protected static class WidgetLocation {
        public final WAbsolutePanel parent;

        public final BWidget widget;

        public final double ax, bx, ay, by;

        /**
         * Create a new WidgetLocation
         *
         * @param parent the parent panel
         * @param widget the widget
         * @param ax     the a value of the x equation
         * @param bx     the b value of the x equation
         * @param ay     the a value of the y equation
         * @param by     the b value of the y equation
         */
        public WidgetLocation(WAbsolutePanel parent, BWidget widget, double ax, double bx, double ay, double by) {
            this.parent = parent;
            this.widget = widget;
            this.ax = ax;
            this.bx = bx;
            this.ay = ay;
            this.by = by;
        }

        /**
         * Get the {@link com.hazeltrinity.blossom.gui.widget.BWidget.ChildWidget} from this WidgetLocation
         *
         * @return the ChildWidget
         */
        public ChildWidget getChild() {
            return new ChildWidget(
            widget,
            (int) (ax * (parent.getWidth() - parent.leftMargin - parent.rightMargin) + bx) + parent.leftMargin,
            (int) (ay * (parent.getHeight() - parent.topMargin - parent.bottomMargin) + by) + parent.topMargin
            );
        }

        /**
         * Get how big a parent panel must be to have this widget fit, should not be modified.
         *
         * @return the required size.
         */
        public Size getRequiredSize() {
            return parent.addMargins(new Size(
            ceil((bx + widget.getWidth()) / (1 - ax)),
            ceil((by + widget.getHeight()) / (1 - ay))
            ));
        }

        private int ceil(double x) {
            return (int) Math.ceil(x);
        }
    }
}