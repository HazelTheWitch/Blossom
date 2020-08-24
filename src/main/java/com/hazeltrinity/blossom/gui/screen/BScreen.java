package com.hazeltrinity.blossom.gui.screen;

import com.hazeltrinity.blossom.gui.widget.BNamedWidget;
import com.hazeltrinity.blossom.gui.widget.BWidget.Size;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class BScreen extends Screen {

    public final BDescription description;

    private int left, top;

    public static BScreen of(BDescription description) {
        if (description.root instanceof BNamedWidget) {
            return new BScreen(((BNamedWidget) description.root).getName(), description);
        } else {
            return new BScreen(description);
        }
    }

    public BScreen(BDescription description) {
        this("", description);
    }

    public BScreen(String title, BDescription description) {
        this(new LiteralText(title), description);
    }

    public BScreen(Text title, BDescription description) {
        super(title);

        this.description = description;
    }

    @Override
    public void init(MinecraftClient client, int screenWidth, int screenHeight) {
        super.init(client, screenWidth, screenHeight);

        reposition(screenWidth, screenHeight);
    }

    public void reposition(int width, int height) {
        description.root.invalidateCachedSize();

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

        description.root.invalidateCachedSize();

        description.root.paint(matrices, left, top, mouseX - left, mouseY - top);

        super.render(matrices, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        description.root.tick();
    }
}