package com.hazeltrinity.blossom.init;

import com.hazeltrinity.blossom.BMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

/**
 * Used as a general entrypoint, implements all 4 entrypoints but should only use one to prevent modloading issues.
 */
public abstract class BModInitializer implements ModInitializer, ClientModInitializer, DedicatedServerModInitializer, PreLaunchEntrypoint {

    @Override
    public void onInitialize() {
        getMod().onInitialize();
    }

    @Override
    public void onInitializeClient() {
        getMod().onInitializeClient();
    }

    @Override
    public void onInitializeServer() {
        getMod().onInitializeServer();
    }

    @Override
    public void onPreLaunch() {
        getMod().onPreLaunch();
    }

    /**
     * Get the instance of a Blossom mod. Must only return a single Mod instance at all entry points.
     *
     * @return the Blossom mod
     */
    public abstract BMod getMod();
}