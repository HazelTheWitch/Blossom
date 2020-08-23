package com.hazeltrinity.blossom.gui.drawing;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BDrawing {
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
    public static void drawString(MatrixStack matrices, String text, int x, int y, double alignment, int color) {
        MinecraftClient client = MinecraftClient.getInstance();

        int width = client.textRenderer.getWidth(text);

        x -= (int)(width * alignment);

        client.textRenderer.draw(matrices, text, x, y, color);
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
    public static void drawString(MatrixStack matrices, Text text, int x, int y, double alignment, int color) {
        drawString(matrices, text.asString(), x, y, alignment, color);
    }

    /**
     * Draw a textured rectangle to the screen.
     * 
     * Lovingly taken from
     * {@link https://github.com/CottonMC/LibGui/blob/7fbd5d5c81e9ea0d7cb8ec179c6ae1418ef45efc/src/main/java/io/github/cottonmc/cotton/gui/client/ScreenDrawing.java#L118}
     * 
     * @param x       the x
     * @param y       the y
     * @param width   the width of the rectangle
     * @param height  the height of the rectangle
     * @param texture the texture
     * @param u1
     * @param v1
     * @param u2
     * @param v2
     * @param color
     * @param opacity
     */
    public static void drawTexture(int x, int y, int width, int height, Identifier texture, float u1, float v1, float u2, float v2, int color, float opacity) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);

        if (width <= 0) width = 1;
        if (height <= 0) height = 1;

        float r = (color >> 16 & 255) / 255f;
        float g = (color >> 8 & 255) / 255f;
        float b = (color & 255) / 255f;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        RenderSystem.enableBlend();

        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        buffer.vertex(x        , y + height, 0).color(r, g, b, opacity).texture(u1, v2).next();
        buffer.vertex(x + width, y + height, 0).color(r, g, b, opacity).texture(u2, v2).next();
        buffer.vertex(x + width, y         , 0).color(r, g, b, opacity).texture(u2, v1).next();
        buffer.vertex(x        , y         , 0).color(r, g, b, opacity).texture(u1, v1).next();
        tessellator.draw();
        RenderSystem.disableBlend();
    }

    public static void drawRect(int x, int y, int width, int height, int color) {
        if (width <= 0)
            width = 1;
        if (height <= 0)
            height = 1;

        float a = (color >> 24 & 255) / 255.0F;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(x        , y + height, 0.0D).color(r, g, b, a).next();
        buffer.vertex(x + width, y + height, 0.0D).color(r, g, b, a).next();
        buffer.vertex(x + width, y         , 0.0D).color(r, g, b, a).next();
        buffer.vertex(x        , y         , 0.0D).color(r, g, b, a).next();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawGuiPanel(int x, int y, int width, int height) {
        drawGuiPanel(x, y, width, height, 0xFF555555, 0xFFC6C6C6, 0xFFFFFFFF, 0xFF000000);
    }

    public static void drawGuiPanel(int x, int y, int width, int height, int shadow, int panel, int hilight, int outline) {
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
        drawBeveledPanel(x, y, 18, 18, 0xFF373737, 0xFF8b8b8b, 0xFFFFFFFF);
    }

    public static void drawBeveledPanel(int x, int y, int width, int height) {
        drawBeveledPanel(x, y, width, height, 0xFF373737, 0xFF8b8b8b, 0xFFFFFFFF);
    }

    public static void drawBeveledPanel(int x, int y, int width, int height, int topleft, int panel, int bottomright) {
        drawRect(x, y, width, height, panel); // Center panel
        drawRect(x, y, width - 1, 1, topleft); // Top shadow
        drawRect(x, y + 1, 1, height - 2, topleft); // Left shadow
        drawRect(x + width - 1, y + 1, 1, height - 1, bottomright); // Right hilight
        drawRect(x + 1, y + height - 1, width - 1, 1, bottomright); // Bottom hilight
    }
}