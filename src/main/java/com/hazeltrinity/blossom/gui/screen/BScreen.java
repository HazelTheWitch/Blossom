package com.hazeltrinity.blossom.gui.screen;

import com.hazeltrinity.blossom.gui.widget.BNamedWidget;
import com.hazeltrinity.blossom.gui.widget.BWidget.Size;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

/**
 * Represents a client sided screen. Can not include any item slots, or anything to communicate with the server
 */
@Environment(EnvType.CLIENT)
public class BScreen extends Screen {

    public final BDescription description;

    private int left, top;

    /**
     * Create a new screen from a description, with no title.
     *
     * @param description the description to make the screen after.
     */
    public BScreen(BDescription description) {
        this("", description);
    }

    /**
     * Create a new screen from a description, with a literal title.
     *
     * @param title       the literal title of the screen
     * @param description the description to make the screen after.
     */
    public BScreen(String title, BDescription description) {
        this(new LiteralText(title), description);
    }

    /**
     * Create a new screen from a description, with a text title.
     *
     * @param title       the text title of the screen
     * @param description the description to make the screen after.
     */
    public BScreen(Text title, BDescription description) {
        super(title);

        this.description = description;
    }

    /**
     * Create a new {@link BScreen} with the title of the root widget of the description, if it is named or no title.
     *
     * @param description the description to use
     *
     * @return a new {@link BScreen}
     */
    public static BScreen of(BDescription description) {
        if (description.root instanceof BNamedWidget) {
            return new BScreen(((BNamedWidget) description.root).getName(), description);
        } else {
            return new BScreen(description);
        }
    }

    @Override
    public void init(MinecraftClient client, int screenWidth, int screenHeight) {
        super.init(client, screenWidth, screenHeight);

        reposition(screenWidth, screenHeight);
    }

    private void reposition(int width, int height) {
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

        matrices.push();
        matrices.translate(left, top, 0);
        description.root.paint(matrices, mouseX - left, mouseY - top);
        matrices.pop();

        super.render(matrices, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        description.root.tick();
    }
}