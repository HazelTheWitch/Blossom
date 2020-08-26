package com.hazeltrinity.blossom.gui.screen;

import com.hazeltrinity.blossom.gui.widget.BWidget;

import java.util.List;

/**
 * Describes a screen, can be passed to either a {@link BScreen} or {@link BHandledScreen}, display the same on either.
 */
public class BDescription {
    public final BWidget root;
    private final List<BWidget> focusQueue = null;
    private BWidget focus;
    private boolean fullscreen;

    private BDescription(BWidget root) {
        this.root = root;

        for (BWidget widget : root.getAncestors()) {
            widget.setDescription(this);
        }
    }

    /**
     * Should the screen be rendered from the top left of the screen or centered.
     *
     * @return if this screen is fullscreen
     */
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

    /**
     * Get the currently focussed {@link BWidget}
     *
     * @return the focus of this description
     */
    public BWidget getFocus() {
        return focus;
    }

    /**
     * Builder class for descriptions
     */
    public static class Builder {

        private final BWidget root;

        private boolean fullscreen = false;

        /**
         * Create a builder.
         *
         * @param root the root widget of this description
         */
        public Builder(BWidget root) {
            this.root = root;
        }

        /**
         * Tells the description if this screen will be fullscreen
         *
         * @param fullscreen is this screen fullscreen
         *
         * @return this Builder for chaining
         */
        public Builder isFullscreen(boolean fullscreen) {
            this.fullscreen = fullscreen;
            return this;
        }

        /**
         * Create a new {@link BDescription} from the builder.
         *
         * @return the final {@link BDescription}
         */
        public BDescription build() {
            BDescription description = new BDescription(root);

            description.fullscreen = fullscreen;

            return description;
        }
    }
}