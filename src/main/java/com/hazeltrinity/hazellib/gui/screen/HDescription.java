package com.hazeltrinity.hazellib.gui.screen;

import com.hazeltrinity.hazellib.gui.widget.HWidget;

public class HDescription {
    public static class Builder {
        private HWidget root;

        private boolean fullscreen = false;

        private int titleOffsetX = 10;
        private int titleOffsetY = 10;

        private boolean visibleTitle = true;

        private double titleAlignment = 0.5;

        private int titleColor = 0x404040;

        public Builder(HWidget root) {
            this.root = root;
        }

        public Builder isFullscreen(boolean fullscreen) {
            this.fullscreen = fullscreen;
            return this;
        }

        public Builder titleOffset(int x, int y) {
            titleOffsetX = x;
            titleOffsetY = y;
            return this;
        }

        public Builder visibleTitle(boolean visible) {
            visibleTitle = visible;
            return this;
        }

        public Builder titleAlignment(double alignment) {
            titleAlignment = alignment;
            return this;
        }

        public Builder titleColor(int color) {
            titleColor = color;
            return this;
        }

        public HDescription build() {
            HDescription description = new HDescription(root);

            description.fullscreen = fullscreen;
            
            description.titleOffsetX = titleOffsetX;
            description.titleOffsetY = titleOffsetY;

            description.visibleTitle = visibleTitle;

            description.titleAlignment = titleAlignment;

            description.titleColor = titleColor;

            return description;
        }
    }

    public final HWidget root;

    private boolean fullscreen;

    private int titleOffsetX;
    private int titleOffsetY;

    private boolean visibleTitle;

    private double titleAlignment;

    private int titleColor;

    private HDescription(HWidget root) {
        this.root = root;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public int getTitleOffsetX() {
        return titleOffsetX;
    }

    public int getTitleOffsetY() {
        return titleOffsetY;
    }

    public boolean isTitleVisible() {
        return visibleTitle;
    }

    public double getTitleAlignment() {
        return titleAlignment;
    }

    public int getTitleColor() {
        return titleColor;
    }
}