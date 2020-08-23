package com.hazeltrinity.blossom.gui.widget;

import java.util.List;

import com.hazeltrinity.blossom.gui.widget.BWidget.ChildWidget;
import com.hazeltrinity.blossom.gui.widget.BWidget.Size;

import org.jetbrains.annotations.Nullable;

/**
 * A panel is an Object which holes child widgets.
 */
public interface Panel {
    /**
     * Get all children of this widget. Does not include the children of children or
     * any deeper.
     * @param height
     * @param width
     * 
     * @return a list of all children of this widget
     */
    @Nullable
    public List<ChildWidget> getChildren(int width, int height);

    /**
     * Get reccommended size of this panel.
     * 
     * @return the size hint
     */
    public Size getSizeHint();
}