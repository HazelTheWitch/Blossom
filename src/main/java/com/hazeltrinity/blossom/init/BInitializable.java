package com.hazeltrinity.blossom.init;

import com.hazeltrinity.blossom.BMod;

public abstract class BInitializable {
    protected BMod mod;

    /**
     * Always called before initialization.
     *
     * @param mod the mod this initializable will be initialized with
     * @throws IllegalStateException if called multiple times
     */
    public void setMod(BMod mod) throws IllegalStateException {
        if (this.mod != null) {
            throw new IllegalStateException("Attempted to set multiple mods (" + this.mod.name + ", " + mod.name + ").");
        }

        this.mod = mod;
    }

    /**
     * Client/Server is called <b>asynchronously</b> during initialization. Do not count on one being called before the other.
     */
    public void onInitialize() {
    }

    public void onInitializeClient() {
    }

    public void onInitializeServer() {
    }

    /**
     * Called before the initialization phase. Not for general use.
     */
    public void onPreLaunch() {
    }
}