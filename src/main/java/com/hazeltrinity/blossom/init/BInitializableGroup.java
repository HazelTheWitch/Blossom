package com.hazeltrinity.blossom.init;

import com.hazeltrinity.blossom.BMod;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Group Initializables together, allows operations to affect all of the initializables.
 */
public class BInitializableGroup extends BInitializable {

    private final HashSet<BInitializable> initializables;

    /**
     * Create an empty initialization group.
     */
    public BInitializableGroup() {
        initializables = new HashSet<BInitializable>();
    }

    /**
     * Create an initialization group from initializables.
     *
     * @param initial the initial initializables
     */
    public BInitializableGroup(BInitializable... initial) {
        this(Arrays.asList(initial));
    }

    /**
     * Create an initialization group from initializables.
     *
     * @param initial the initial initializables
     */
    public BInitializableGroup(Collection<BInitializable> initial) {
        initializables = new HashSet<BInitializable>(initial);
    }

    public void setMod(BMod mod) throws IllegalStateException {
        for (BInitializable initializable : initializables) {
            initializable.setMod(mod);
        }
    }

    public void onInitialize() {
        for (BInitializable initializable : initializables) {
            initializable.onInitialize();
        }
    }

    public void onInitializeClient() {
        for (BInitializable initializable : initializables) {
            initializable.onInitializeClient();
        }
    }

    public void onInitializeServer() {
        for (BInitializable initializable : initializables) {
            initializable.onInitializeServer();
        }
    }

    public void onPreLaunch() {
        for (BInitializable initializable : initializables) {
            initializable.onPreLaunch();
        }
    }
}