package com.hazeltrinity.blossom.gui.widget;

import com.hazeltrinity.blossom.gui.widget.BWidget.ChildWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * A parent is an Object which holds child widgets.
 */
public interface Parent {
    /**
     * Get all children of this widget. Does not include the children of children or any deeper.
     *
     * @return a list of all children of this widget
     */
    default List<ChildWidget> getChildren() {
        return new ArrayList<>();
    }
}