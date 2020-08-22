package com.hazeltrinity.hazellib.init;

import com.hazeltrinity.hazellib.HMod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public abstract class HModInitializer implements ModInitializer, ClientModInitializer, DedicatedServerModInitializer, PreLaunchEntrypoint {

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

    public abstract HMod getMod();
}