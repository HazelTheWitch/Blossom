package com.hazeltrinity.blossomtest;

import com.hazeltrinity.blossom.BMod;
import com.hazeltrinity.blossom.init.BGroup;
import com.hazeltrinity.blossom.init.BModInitializer;

public class BlossomTestMod extends BModInitializer {

    public static final BMod MOD = new BMod("Blossom Test Mod", "blossomtest");

    public static final BGroup TestClientScreen = MOD.register(new BGroup("test_client").setItem(new TestItem()));

    @Override
    public BMod getMod() {
        return MOD;
    }
}