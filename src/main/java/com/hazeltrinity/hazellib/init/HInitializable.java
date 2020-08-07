package com.hazeltrinity.hazellib.init;

public abstract class HInitializable {
    protected HMod mod;

    /**
     * Always called before initialization.
     * @throws IllegalStateException if called multiple times
     * @param mod the mod this initializable will be initialized with
     */
    public void setMod(HMod mod) throws IllegalStateException {
        if (this.mod == null) {
            throw new IllegalStateException();
        }

        this.mod = mod;
    }

    /**
     * Called <b>asynchronously</b> during initialization. Do not count on one being called before the other.
     */
    public void onInitialize() {}
    public void onInitializeClient() {}
    public void onInitializeServer() {}

    /**
     * Called before the initialization phase. Use sparringly.
     */
    public void onPreLaunch() {}
}