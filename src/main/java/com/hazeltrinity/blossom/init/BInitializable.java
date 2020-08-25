package com.hazeltrinity.blossom.init;

import com.hazeltrinity.blossom.BMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;

public abstract class BInitializable {

    protected BMod mod;

    private final ArrayList<BInitializable> tackedOn = new ArrayList<>();

    // TODO: Ensure no recursion with hashset

    /**
     * Add another {@code BInitializable} to be tacked on after this one's initialization step.
     *
     * @param other the other {@code BInitializable}
     * @return this for chaining
     */
    public BInitializable tackOn(BInitializable other) {
        tackedOn.add(other);
        return this;
    }

    /**
     * Always called before initialization.
     *
     * @param mod the mod this initializable will be initialized with
     *
     * @throws IllegalStateException if called multiple times
     */
    public void setMod(BMod mod) throws IllegalStateException {
        if (this.mod != null) {
            throw new IllegalStateException("Attempted to set multiple mods (" + this.mod.name + ", " + mod.name + ").");
        }

        this.mod = mod;
    }

    /**
     * Client/Server is called <b>asynchronously</b> during initialization. Do not count on one being called before the
     * other.
     */
    public void onInitialize() {
        for (BInitializable other : tackedOn) {
            other.onInitialize();
        }
    }

    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        for (BInitializable other : tackedOn) {
            other.onInitializeClient();
        }
    }

    @Environment(EnvType.SERVER)
    public void onInitializeServer() {
        for (BInitializable other : tackedOn) {
            other.onInitializeServer();
        }
    }

    /**
     * Called before the initialization phase. Not for general use.
     */
    public void onPreLaunch() {
        for (BInitializable other : tackedOn) {
            other.onPreLaunch();
        }
    }
}