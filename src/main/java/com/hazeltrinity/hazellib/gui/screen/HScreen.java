package com.hazeltrinity.hazellib.gui.screen;

import com.hazeltrinity.hazellib.gui.drawing.HDrawing;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class HScreen extends Screen {
    public final HDescription description;

    private int left, top;
    /**
     * Root width and height of the screen.
     */
    private int width, height;
    private int titleX, titleY;
    
    public HScreen(HDescription description) {
        this("", description);
    }

    public HScreen(String title, HDescription description) {
        this(new LiteralText(title), description);
    }

    public HScreen(Text title, HDescription description) {
        super(title);

        this.description = description;
    }

    @Override
    public void init(MinecraftClient client, int screenWidth, int screenHeight) {
        super.init(client, screenWidth, screenHeight);

        reposition(screenWidth, screenHeight);
    }

    public void reposition(int width, int height) {
        if (description.isFullscreen()) {
            left = 0;
            top = 0;
        } else {
            left = (width - description.root.getScaler().getWidth(width, height)) / 2;
            top = (height - description.root.getScaler().getHeight(width, height)) / 2;
        }

        titleX = description.getTitleOffsetX();
        titleY = description.getTitleOffsetY();

        this.width = width;
        this.height = height;
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(matrices);

        description.root.paintWithChildren(matrices, left, top, width, height, mouseX - left, mouseY - top);

        if (title != null && description.isTitleVisible()) {
            HDrawing.drawString(matrices, title, left + titleX + width / 2, top + titleY, description.getTitleAlignment(), description.getTitleColor());
        }

        super.render(matrices, mouseX, mouseY, partialTicks);
    }

    @Override
	public void tick() {
        super.tick();
        description.root.tick();
    }
}