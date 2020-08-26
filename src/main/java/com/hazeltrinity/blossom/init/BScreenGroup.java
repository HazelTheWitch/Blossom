package com.hazeltrinity.blossom.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry.Factory;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.ExtendedClientHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.SimpleClientHandlerFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

/**
 * A screenhandler, screen pair. Used to avoid having generics in {@link BInitializable}.
 */
public class BScreenGroup<V extends H, H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>> extends BInitializable {

    @Environment(EnvType.CLIENT)
    private ScreenRegistry.Factory<H, S> screen;
    private ScreenHandlerType<V> screenHandlerType;

    private final Identifier identifier;

    /**
     * Create a new ScreenGroup
     *
     * @param identifier the identifier
     */
    BScreenGroup(Identifier identifier) {
        this.identifier = identifier;
    }

    /**
     * Set this group's screen
     *
     * @param screen the screen factory to set
     *
     * @return this for chaining
     */
    @Environment(EnvType.CLIENT)
    public BScreenGroup<V, H, S> setScreen(Factory<H, S> screen) {
        this.screen = screen;
        return this;
    }

    /**
     * Set this group's simple screen handler
     *
     * @param screenHandler the screen handler factory to set
     *
     * @return this for chaining
     *
     * @throws IllegalStateException if this has already been set
     */
    public BScreenGroup<V, H, S> setScreenHandlerSimple(SimpleClientHandlerFactory<V> screenHandler) throws IllegalStateException {
        if (screenHandlerType != null) {
            throw new IllegalStateException("Screen Handler type already set.");
        }
        screenHandlerType = ScreenHandlerRegistry.registerSimple(identifier, screenHandler);
        return this;
    }

    /**
     * Set this group's simple screen handler
     *
     * @param screenHandler the screen handler factory to set
     *
     * @return this for chaining
     *
     * @throws IllegalStateException if this has already been set
     */
    public BScreenGroup<V, H, S> setScreenHandlerExtended(ExtendedClientHandlerFactory<V> screenHandler) throws IllegalStateException {
        if (screenHandlerType != null) {
            throw new IllegalStateException("Screen Handler type already set.");
        }
        screenHandlerType = ScreenHandlerRegistry.registerExtended(identifier, screenHandler);
        return this;
    }

    /**
     * Get the screen handler type
     *
     * @return the screen handler type
     */
    public ScreenHandlerType<V> getScreenHandlerType() {
        return screenHandlerType;
    }

    @Override
    public void onInitializeClient() {
        super.onInitializeClient();

        if (screen != null) {
            ScreenRegistry.register(screenHandlerType, screen);
        }
    }
}
