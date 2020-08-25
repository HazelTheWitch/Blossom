package com.hazeltrinity.blossomtest;

import com.hazeltrinity.blossom.BMod;
import com.hazeltrinity.blossom.init.BGroup;
import com.hazeltrinity.blossom.init.BModInitializer;
import net.minecraft.item.Item;

public class BlossomTestMod extends BModInitializer {

    public static final BMod MOD = new BMod("Blossom Test Mod", "blossomtest");

    public static final BGroup TestClientScreen = MOD.register(new BGroup("test_client").setItem(new TestItem()));

    public static final BGroup TestServerScreen = MOD.register(
        new BGroup("test_server")
        .setBlock(new TestBlock())
        .setBlockItem(new Item.Settings())
        .setBlockEntityType(TestBlockEntity::new)
        .<TestScreenHandler, TestScreen>setScreenHandlerSimple(
            TestScreenHandler::new,
            TestScreen::new
        )
    );

    @Override
    public BMod getMod() {
        return MOD;
    }
}