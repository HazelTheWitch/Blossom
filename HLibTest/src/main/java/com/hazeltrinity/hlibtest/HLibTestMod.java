package com.hazeltrinity.hlibtest;

import com.hazeltrinity.hazellib.init.HGroup;
import com.hazeltrinity.hazellib.init.HMod;
import com.hazeltrinity.hazellib.init.HModInitializer;

public class HLibTestMod extends HModInitializer {

	public static final HMod MOD = new HMod("HazelLib Test Mod", "hlibtest");
	
	public static final HGroup TestClientScreen = MOD
			.register(new HGroup("test_client").setItem(new TestItem()));

	@Override
	public HMod getMod() {
		return MOD;
	}
}