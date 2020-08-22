package com.hazeltrinity.hazellib.gui.widget.impl;

import java.util.ArrayList;
import java.util.List;

import com.hazeltrinity.hazellib.gui.widget.HNamedWidget;
import com.hazeltrinity.hazellib.gui.widget.HWidget;
import com.hazeltrinity.hazellib.gui.widget.Panel;

import org.jetbrains.annotations.Nullable;

import net.minecraft.text.Text;

/**
 * Organizes widgets by their absolute position on the panel, either by % or
 * constant offset.
 */
public class AbsolutePanel extends HNamedWidget implements Panel {
    protected List<WidgetLocation> children = new ArrayList<WidgetLocation>();

    /**
     * Creates a new HNamedWidget centered in the widget, looks vanilla.
     * 
     * @param name        the title of the widget
     */
    public AbsolutePanel(Text name) {
        super(name);
    }

    public AbsolutePanel(Size minimumSize) {
        super(minimumSize);
    }

    /**
     * Creates a new HNamedWidget centered in the widget, looks vanilla.
     * 
     * @param minimumSize the minimum size of the widget
     * @param name        the title of the widget
     */
    public AbsolutePanel(Size minimumSize, Text name) {
        super(minimumSize, name);
    }

    /**
     * Create a new HNamedWidget with minimum size 0, 0.
     * 
     * @param name            the title of the widget
     * @param widgetAlignment the alignment within the widget
     * @param textAlignment   the alignment of the text
     * @param color           the color to draw the title
     */
    public AbsolutePanel(Text name, double widgetAlignment, double textAlignment, int color) {
        super(name, widgetAlignment, textAlignment, color);
    }

    /**
     * Create a new HNamedWidget
     * 
     * @param minimumSize     the minimum size of the widget
     * @param name            the title of the widget
     * @param widgetAlignment the alignment within the widget
     * @param textAlignment   the alignment of the text
     * @param color           the color to draw the title
     */
    public AbsolutePanel(Size minimumSize, Text name, double widgetAlignment, double textAlignment, int color) {
        super(minimumSize, name, widgetAlignment, textAlignment, color);
    }

    public AbsolutePanel addChild(HWidget widget, double ax, double bx, double ay, double by) {
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
        public final HWidget widget;

        public final double ax, bx, ay, by;

        public WidgetLocation(HWidget widget, double ax, double bx, double ay, double by) {
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