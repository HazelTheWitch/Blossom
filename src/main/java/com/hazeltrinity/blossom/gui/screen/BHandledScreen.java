package com.hazeltrinity.blossom.gui.screen;

import com.hazeltrinity.blossom.gui.widget.BNamedWidget;
import com.hazeltrinity.blossom.gui.widget.BWidget.Size;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class BHandledScreen<T extends BScreenHandler> extends HandledScreen<T> implements ScreenHandlerProvider<T> {

    public final BDescription description;

    private int left, top;

    public BHandledScreen(T handler, PlayerInventory inventory, String title) {
        this(handler, inventory, new LiteralText(title));
    }

    public BHandledScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.description = handler.description;
    }

    public BHandledScreen<T> of(T handler, PlayerInventory inventory, Text title) {
        if (description.root instanceof BNamedWidget) {
            return new BHandledScreen<>(handler, inventory, ((BNamedWidget) description.root).getName());
        } else {
            return new BHandledScreen<>(handler, inventory, new LiteralText(""));
        }
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        // This is empty because widgets should draw their own background.
        // Eventually maybe will change widgets to have a "draw background" flag, and leave it to each widget.
    }

    @Override
    public boolean isPauseScreen() {
        return false;
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
        description.root.invalidateCachedSize();
        description.root.paint(matrices, left, top, mouseX - left, mouseY - top);

        super.render(matrices, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        description.root.tick();
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        // Override to disable drawing the title and player inventory
    }
}
