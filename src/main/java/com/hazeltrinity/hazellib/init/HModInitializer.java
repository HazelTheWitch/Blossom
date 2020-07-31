package com.hazeltrinity.hazellib.init;

public abstract class HModInitializer<M extends HMod> {
    protected HMod getMod() {
        return M.getMod();
    }

    public void onInitialize() {
        getMod().onInitialize();
    }

    public void onInitializeClient() {
        getMod().onInitializeClient();
    }

    public void onInitializeServer() {
        getMod().onInitializeServer();
    }

    public void onPreLaunch() {
        getMod().onPreLaunch();
    }
}