package com.hazeltrinity.hazellib.gui.screen;

import com.hazeltrinity.hazellib.gui.widget.HWidget;

public class HDescription {
    public static class Builder {
        private HWidget root;

        private boolean fullscreen = false;

        public Builder(HWidget root) {
            this.root = root;
        }

        public Builder isFullscreen(boolean fullscreen) {
            this.fullscreen = fullscreen;
            return this;
        }

        public HDescription build() {
            HDescription description = new HDescription(root);

            description.fullscreen = fullscreen;

            return description;
        }
    }

    public final HWidget root;

    private boolean fullscreen;

    private HDescription(HWidget root) {
        this.root = root;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }
}