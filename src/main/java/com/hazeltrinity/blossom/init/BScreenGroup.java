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

public class BScreenGroup<V extends H, H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>> extends BInitializable {

    @Environment(EnvType.CLIENT)
    private ScreenRegistry.Factory<H, S> screen;
    private ScreenHandlerType<V> screenHandlerType;

    private final Identifier identifier;

    public BScreenGroup(Identifier identifier) {
        this.identifier = identifier;
    }

    @Environment(EnvType.CLIENT)
    public BScreenGroup<V, H, S> setScreen(Factory<H, S> screen) {
        this.screen = screen;
        return this;
    }

    public BScreenGroup<V, H, S> setScreenHandlerSimple(SimpleClientHandlerFactory<V> screenHandler) {
        screenHandlerType = ScreenHandlerRegistry.registerSimple(identifier, screenHandler);
        return this;
    }

    public BScreenGroup<V, H, S> setScreenHandlerExtended(ExtendedClientHandlerFactory<V> screenHandler) {
        screenHandlerType = ScreenHandlerRegistry.registerExtended(identifier, screenHandler);
        return this;
    }

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
