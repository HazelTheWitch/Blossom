package com.hazeltrinity.blossomtest;

import com.hazeltrinity.blossom.BMod;
import com.hazeltrinity.blossom.init.BModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class BlossomTestModClient extends BModInitializer {
    @Override
    public BMod getMod() {
        return BlossomTestMod.MOD;
    }
}
