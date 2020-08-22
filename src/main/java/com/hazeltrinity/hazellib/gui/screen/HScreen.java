package com.hazeltrinity.hazellib.gui.screen;

import com.hazeltrinity.hazellib.gui.widget.HNamedWidget;
import com.hazeltrinity.hazellib.gui.widget.HWidget.Size;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class HScreen extends Screen {
    public final HDescription description;

    private int left, top;
    private int width, height;
    
    public static HScreen of(HDescription description) {
        if (description.root instanceof HNamedWidget) {
            return new HScreen(((HNamedWidget)description.root).getName(), description);
        } else {
            return new HScreen(description);
        }
    }

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
            Size rootSize = description.root.getSize();
            left = (width - rootSize.width) / 2;
            top = (height - rootSize.height) / 2;
        }

        this.width = width;
        this.height = height;
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(matrices);

        Size rootSize = description.root.getSize();
        description.root.paintWithChildren(matrices, left, top, rootSize.width, rootSize.height, mouseX - left, mouseY - top);

        super.render(matrices, mouseX, mouseY, partialTicks);
    }

    @Override
	public void tick() {
        super.tick();
        description.root.tickWithChildren(width, height);
    }
}