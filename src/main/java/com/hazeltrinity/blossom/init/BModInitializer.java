package com.hazeltrinity.blossom.init;

import com.hazeltrinity.blossom.BMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

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

    public abstract BMod getMod();
}