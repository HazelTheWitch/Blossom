package com.hazeltrinity.blossom.gui.drawing;

import com.hazeltrinity.blossom.gui.BColor;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.opengl.GL11;

/**
 * Helper class for rendering UI elements.
 */
@Environment(EnvType.CLIENT)
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
     * @param alignment the alignment of the string, 0 = left, 0.5 = center, 1 = right
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
     * @param alignment the alignment of the string, 0 = left, 0.5 = center, 1 = right
     * @param color     the color to draw
     */
    public static void drawString(MatrixStack matrices, Text text, int x, int y, double alignment, BColor color) {
        drawString(matrices, text.asString(), x, y, alignment, color);
    }

    /**
     * Draw a textured rectangle to the screen.
     * <p>
     * Lovingly taken from {@link https://github.com/CottonMC/LibGui/blob/7fbd5d5c81e9ea0d7cb8ec179c6ae1418ef45efc/src/main/java/io/github/cottonmc/cotton/gui/client/ScreenDrawing.java#L118}
     *
     * @param matrices the matrix stack to use for drawing
     * @param x        the x
     * @param y        the y
     * @param width    the width of the rectangle
     * @param height   the height of the rectangle
     * @param texture  the texture
     * @param u1       u coordinate 1
     * @param v1       v coordinate 1
     * @param u2       u coordinate 2
     * @param v2       v coordinate 2
     * @param color    the color to tint the texture, alpha is included
     */
    public static void drawTexture(MatrixStack matrices, int x, int y, int width, int height, Identifier texture, float u1, float v1, float u2, float v2, BColor color) {
        Matrix4f matrix = matrices.peek().getModel();

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
        buffer.vertex(matrix, x, y + height, 0).color(r, g, b, opacity).texture(u1, v2).next();
        buffer.vertex(matrix, x + width, y + height, 0).color(r, g, b, opacity).texture(u2, v2).next();
        buffer.vertex(matrix, x + width, y, 0).color(r, g, b, opacity).texture(u2, v1).next();
        buffer.vertex(matrix, x, y, 0).color(r, g, b, opacity).texture(u1, v1).next();

        tessellator.draw();
        RenderSystem.disableBlend();
    }

    /**
     * Draw a flat colored rectangle.
     *
     * @param matrices the matrix stack to use for drawing
     * @param x        the x coordinate of the top left of the rectangle
     * @param y        the y coordinate of the top left of the rectangle
     * @param width    the width of the rectangle
     * @param height   the height of the rectangle
     * @param color    the color of the rectangle
     */
    public static void drawRect(MatrixStack matrices, int x, int y, int width, int height, BColor color) {
        Matrix4f matrix = matrices.peek().getModel();

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
        buffer.vertex(matrix, x, y + height, 0.0F).color(r, g, b, a).next();
        buffer.vertex(matrix, x + width, y + height, 0.0F).color(r, g, b, a).next();
        buffer.vertex(matrix, x + width, y, 0.0F).color(r, g, b, a).next();
        buffer.vertex(matrix, x, y, 0.0F).color(r, g, b, a).next();

        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    /**
     * Draws a GUI panel in the standard vanilla style.
     *
     * @param matrices the current MatrixStack
     * @param x        the x coordinate of the top left of the GUI
     * @param y        the y coordinate of the top left of the GUI
     * @param width    the width of the GUI panel
     * @param height   the height of the GUI panel
     */
    public static void drawGuiPanel(MatrixStack matrices, int x, int y, int width, int height) {
        drawGuiPanel(matrices, x, y, width, height, GUI_SHADOW, GUI_PANEL, GUI_HILIGHT, GUI_OUTLINE);
    }

    /**
     * Draws a GUI panel in the standard vanilla style.
     *
     * @param matrices the current MatrixStack
     * @param x        the x coordinate of the top left of the GUI
     * @param y        the y coordinate of the top left of the GUI
     * @param width    the width of the GUI panel
     * @param height   the height of the GUI panel
     * @param shadow   the shadow in the bottom right of the GUI
     * @param panel    the main panel color
     * @param hilight  the hilight  in the top left of the GUI
     * @param outline  the outline surrounding the GUI
     */
    public static void drawGuiPanel(MatrixStack matrices, int x, int y, int width, int height, BColor shadow, BColor panel, BColor hilight, BColor outline) {

        drawRect(matrices, x + 3, y + 3, width - 6, height - 6, panel); // Main panel area

        drawRect(matrices, x + 2, y + 1, width - 4, 2, hilight); // Top hilight
        drawRect(matrices, x + 2, y + height - 3, width - 4, 2, shadow); // Bottom shadow
        drawRect(matrices, x + 1, y + 2, 2, height - 4, hilight); // Left hilight
        drawRect(matrices, x + width - 3, y + 2, 2, height - 4, shadow); // Right shadow
        drawRect(matrices, x + width - 3, y + 2, 1, 1, panel); // Topright non-hilight/non-shadow transition pixel
        drawRect(matrices, x + 2, y + height - 3, 1, 1, panel); // Bottomleft non-hilight/non-shadow transition pixel
        drawRect(matrices, x + 3, y + 3, 1, 1, hilight); // Topleft round hilight pixel
        drawRect(matrices, x + width - 4, y + height - 4, 1, 1, shadow); // Bottomright round shadow pixel

        drawRect(matrices, x + 2, y, width - 4, 1, outline); // Top outline
        drawRect(matrices, x, y + 2, 1, height - 4, outline); // Left outline
        drawRect(matrices, x + width - 1, y + 2, 1, height - 4, outline); // Right outline
        drawRect(matrices, x + 2, y + height - 1, width - 4, 1, outline); // Bottom outline
        drawRect(matrices, x + 1, y + 1, 1, 1, outline); // Topleft round pixel
        drawRect(matrices, x + 1, y + height - 2, 1, 1, outline); // Bottomleft round pixel
        drawRect(matrices, x + width - 2, y + 1, 1, 1, outline); // Topright round pixel
        drawRect(matrices, x + width - 2, y + height - 2, 1, 1, outline); // Bottomright round pixel
    }

    /**
     * Draw a panel similar to the vanilla item slot look.
     *
     * @param matrices the current MatrixStack
     * @param x        the x coordinate of the top left of the slot
     * @param y        the y coordinate of the top left of the slot
     */
    public static void drawBeveledPanel(MatrixStack matrices, int x, int y) {
        drawBeveledPanel(matrices, x, y, 18, 18, TOP_LEFT_BEVEL, PANEL_BEVEL, BOTTOM_RIGHT_BEVEL);
    }

    /**
     * Draw a panel similar to the vanilla item slot look.
     *
     * @param matrices the current MatrixStack
     * @param x        the x coordinate of the top left of the slot
     * @param y        the y coordinate of the top left of the slot
     * @param width    the width of the panel
     * @param height   the height of the panel
     */
    public static void drawBeveledPanel(MatrixStack matrices, int x, int y, int width, int height) {
        drawBeveledPanel(matrices, x, y, width, height, TOP_LEFT_BEVEL, PANEL_BEVEL, BOTTOM_RIGHT_BEVEL);
    }

    /**
     * Draw a panel similar to the vanilla item slot look.
     *
     * @param matrices    the current MatrixStack
     * @param x           the x coordinate of the top left of the slot
     * @param y           the y coordinate of the top left of the slot
     * @param width       the width of the panel
     * @param height      the height of the panel
     * @param topleft     the top left color of the panel
     * @param panel       the main panel color
     * @param bottomright the bottom right color of the panel
     */
    public static void drawBeveledPanel(MatrixStack matrices, int x, int y, int width, int height, BColor topleft, BColor panel, BColor bottomright) {
        drawRect(matrices, x, y, width, height, panel); // Center panel
        drawRect(matrices, x, y, width - 1, 1, topleft); // Top shadow
        drawRect(matrices, x, y + 1, 1, height - 2, topleft); // Left shadow
        drawRect(matrices, x + width - 1, y + 1, 1, height - 1, bottomright); // Right hilight
        drawRect(matrices, x + 1, y + height - 1, width - 1, 1, bottomright); // Bottom hilight
    }

    private static Vector4f apply(MatrixStack matrices, float x, float y) {
        return apply(matrices, x, y, 0, 1);
    }

    private static Vector4f apply(MatrixStack matrices, float x, float y, float z, float w) {
        Matrix4f matrix = matrices.peek().getModel();
        Vector4f point = new Vector4f(x, y, z, w);
        point.transform(matrix);
        return point;
    }
}