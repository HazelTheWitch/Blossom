package com.hazeltrinity.blossom.gui.screen;

import com.hazeltrinity.blossom.gui.widget.BWidget;

import java.util.List;

public class BDescription {

    public final BWidget root;
    private BWidget focus;
    private boolean fullscreen;
    private final List<BWidget> focusQueue = null;

    private BDescription(BWidget root) {
        this.root = root;

        for (BWidget widget : root.getAncestors()) {
            widget.setDescription(this);
        }
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    /**
     * Choose a new focussed widget. If the new widget can not be focussed, unfocus current focus anyway.
     *
     * @param newFocus the new widget to focus
     */
    public void focus(BWidget newFocus) {
        focus.unfocus();

        if (newFocus.isFocusable()) {
            newFocus.focus();

            focus = newFocus;
        }
    }

    // TODO: Implement focus cycling. AKA tab to select next focusable widget

    /**
     * Choose a new focussed widget. If the new widget can not be focussed, do not change focus.
     *
     * @param newFocus the new widget to focus
     */
    public void requestFocus(BWidget newFocus) {
        if (newFocus.isFocusable()) {
            focus.unfocus();
            newFocus.focus();

            focus = newFocus;
        }
    }

    public BWidget getFocus() {
        return focus;
    }

    public static class Builder {

        private final BWidget root;

        private boolean fullscreen = false;

        public Builder(BWidget root) {
            this.root = root;
        }

        public Builder isFullscreen(boolean fullscreen) {
            this.fullscreen = fullscreen;
            return this;
        }

        public BDescription build() {
            BDescription description = new BDescription(root);

            description.fullscreen = fullscreen;

            return description;
        }
    }
}