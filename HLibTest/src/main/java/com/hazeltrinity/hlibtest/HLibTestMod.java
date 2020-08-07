package com.hazeltrinity.hlibtest;

import com.hazeltrinity.hazellib.init.HMod;

import net.fabricmc.api.ModInitializer;

public class HLibTestMod implements ModInitializer {

    public static final HMod MOD = new HMod("HazelLib Test Mod", "hlibtest");

	@Override
	public void onInitialize() {
		MOD.info("TestMod Initializing");
	}
}