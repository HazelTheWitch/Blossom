package com.hazeltrinity.hazellib.init;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import com.hazeltrinity.hazellib.HMod;

/**
 * Group Initializables together, allows operations to affect all of the initializables.
 */
public class HInitializableGroup extends HInitializable {
    private HashSet<HInitializable> initializables;

    /**
     * Create an empty initialization group.
     */
    public HInitializableGroup() {
        initializables = new HashSet<HInitializable>();
    }

    /**
     * Create an initialization group from initializables.
     * 
     * @param initial the initial initializables
     */
    public HInitializableGroup(HInitializable ... initial) {
        this(Arrays.asList(initial));
    }

    /**
     * Create an initialization group from initializables.
     * 
     * @param initial the initial initializables
     */
    public HInitializableGroup(Collection<HInitializable> initial) {
        initializables = new HashSet<HInitializable>(initial);
    }

    public void setMod(HMod mod) throws IllegalStateException {
        for (HInitializable initializable : initializables) {
            initializable.setMod(mod);
        }
    }

    public void onInitialize() {
        for (HInitializable initializable : initializables) {
            initializable.onInitialize();
        }
    }

    public void onInitializeClient() {
        for (HInitializable initializable : initializables) {
            initializable.onInitializeClient();
        }
    }

    public void onInitializeServer() {
        for (HInitializable initializable : initializables) {
            initializable.onInitializeServer();
        }
    }

    public void onPreLaunch() {
        for (HInitializable initializable : initializables) {
            initializable.onPreLaunch();
        }
    }
}