package com.hazeltrinity.blossom.gui.drawing;

import com.hazeltrinity.blossom.gui.BColor;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class BDrawing {
    public static final BColor TOP_LEFT_BEVEL = BColor.ofColorRGB(0x373737);
    public static final BColor PANEL_BEVEL = BColor.ofColorRGB(0x8b8b8b);
    public static final BColor BOTTOM_RIGHT_BEVEL = BColor.WHITE;

    public static final BColor GUI_SHADOW = BColor.ofColorRGB(0x555555);
    public static final BColor GUI_PANEL = BColor.ofColorRGB(0xC6C6C6);
    public static final BColor GUI_HILIGHT = BColor.WHITE;
    public static final BColor GUI_OUTLINE = BColor.BLACK;

    /**
     * Draws a string to the screen
     *
     * @param matrices  the rendering stack matrices
     * @param text      the text to render
     * @param x         the x position
     * @param y         the y position
     * @param alignment the alignment of the string,
     *                  0 = left, 0.5 = center, 1 = right
     * @param color     the color to draw
     */
    public static void drawString(MatrixStack matrices, String text, int x, int y, double alignment, BColor color) {
        MinecraftClient client = MinecraftClient.getInstance();

        int width = client.textRenderer.getWidth(text);

        x -= (int) (width * alignment);

        client.textRenderer.draw(matrices, text, x, y, color.getColor());
    }

    /**
     * Draws a string to the screen
     *
     * @param matrices  the rendering stack matrices
     * @param text      the text to render
     * @param x         the x position
     * @param y         the y position
     * @param alignment the alignment of the string,
     *                  0 = left, 0.5 = center, 1 = right
     * @param color     the color to draw
     */
    public static void drawString(MatrixStack matrices, Text text, int x, int y, double alignment, BColor color) {
        drawString(matrices, text.asString(), x, y, alignment, color);
    }

    /**
     * Draw a textured rectangle to the screen.
     * <p>
     * Lovingly taken from
     * {@link https://github.com/CottonMC/LibGui/blob/7fbd5d5c81e9ea0d7cb8ec179c6ae1418ef45efc/src/main/java/io/github/cottonmc/cotton/gui/client/ScreenDrawing.java#L118}
     *
     * @param x       the x
     * @param y       the y
     * @param width   the width of the rectangle
     * @param height  the height of the rectangle
     * @param texture the texture
     * @param u1      u coordinate 1
     * @param v1      v coordinate 1
     * @param u2      u coordinate 2
     * @param v2      v coordinate 2
     * @param color   the color to tint the texture, alpha is included
     */
    public static void drawTexture(int x, int y, int width, int height, Identifier texture, float u1, float v1, float u2, float v2, BColor color) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);

        if (width <= 0) width = 1;
        if (height <= 0) height = 1;

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        float opacity = color.getAlpha() / 255f;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        RenderSystem.enableBlend();

        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        buffer.vertex(x, y + height, 0).color(r, g, b, opacity).texture(u1, v2).next();
        buffer.vertex(x + width, y + height, 0).color(r, g, b, opacity).texture(u2, v2).next();
        buffer.vertex(x + width, y, 0).color(r, g, b, opacity).texture(u2, v1).next();
        buffer.vertex(x, y, 0).color(r, g, b, opacity).texture(u1, v1).next();
        tessellator.draw();
        RenderSystem.disableBlend();
    }

    public static void drawRect(int x, int y, int width, int height, BColor color) {
        if (width <= 0)
            width = 1;
        if (height <= 0)
            height = 1;

        float a = color.getAlpha() / 255.0F;
        float r = color.getRed() / 255.0F;
        float g = color.getGreen() / 255.0F;
        float b = color.getBlue() / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(x, y + height, 0.0D).color(r, g, b, a).next();
        buffer.vertex(x + width, y + height, 0.0D).color(r, g, b, a).next();
        buffer.vertex(x + width, y, 0.0D).color(r, g, b, a).next();
        buffer.vertex(x, y, 0.0D).color(r, g, b, a).next();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawGuiPanel(int x, int y, int width, int height) {
        drawGuiPanel(x, y, width, height, GUI_SHADOW, GUI_PANEL, GUI_HILIGHT, GUI_OUTLINE);
    }

    public static void drawGuiPanel(int x, int y, int width, int height, BColor shadow, BColor panel, BColor hilight, BColor outline) {

        drawRect(x + 3, y + 3, width - 6, height - 6, panel); // Main panel area

        drawRect(x + 2, y + 1, width - 4, 2, hilight); // Top hilight
        drawRect(x + 2, y + height - 3, width - 4, 2, shadow); // Bottom shadow
        drawRect(x + 1, y + 2, 2, height - 4, hilight); // Left hilight
        drawRect(x + width - 3, y + 2, 2, height - 4, shadow); // Right shadow
        drawRect(x + width - 3, y + 2, 1, 1, panel); // Topright non-hilight/non-shadow transition pixel
        drawRect(x + 2, y + height - 3, 1, 1, panel); // Bottomleft non-hilight/non-shadow transition pixel
        drawRect(x + 3, y + 3, 1, 1, hilight); // Topleft round hilight pixel
        drawRect(x + width - 4, y + height - 4, 1, 1, shadow); // Bottomright round shadow pixel

        drawRect(x + 2, y, width - 4, 1, outline); // Top outline
        drawRect(x, y + 2, 1, height - 4, outline); // Left outline
        drawRect(x + width - 1, y + 2, 1, height - 4, outline); // Right outline
        drawRect(x + 2, y + height - 1, width - 4, 1, outline); // Bottom outline
        drawRect(x + 1, y + 1, 1, 1, outline); // Topleft round pixel
        drawRect(x + 1, y + height - 2, 1, 1, outline); // Bottomleft round pixel
        drawRect(x + width - 2, y + 1, 1, 1, outline); // Topright round pixel
        drawRect(x + width - 2, y + height - 2, 1, 1, outline); // Bottomright round pixel
    }

    public static void drawBeveledPanel(int x, int y) {
        drawBeveledPanel(x, y, 18, 18, TOP_LEFT_BEVEL, PANEL_BEVEL, BOTTOM_RIGHT_BEVEL);
    }

    public static void drawBeveledPanel(int x, int y, int width, int height) {
        drawBeveledPanel(x, y, width, height, TOP_LEFT_BEVEL, PANEL_BEVEL, BOTTOM_RIGHT_BEVEL);
    }

    public static void drawBeveledPanel(int x, int y, int width, int height, BColor topleft, BColor panel, BColor bottomright) {
        drawRect(x, y, width, height, panel); // Center panel
        drawRect(x, y, width - 1, 1, topleft); // Top shadow
        drawRect(x, y + 1, 1, height - 2, topleft); // Left shadow
        drawRect(x + width - 1, y + 1, 1, height - 1, bottomright); // Right hilight
        drawRect(x + 1, y + height - 1, width - 1, 1, bottomright); // Bottom hilight
    }
}