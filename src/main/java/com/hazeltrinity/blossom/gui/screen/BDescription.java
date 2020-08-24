package com.hazeltrinity.blossom.gui.screen;

import com.hazeltrinity.blossom.gui.widget.BWidget;

public class BDescription {
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

    public final BWidget root;

    private boolean fullscreen;

    private BDescription(BWidget root) {
        this.root = root;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }
}