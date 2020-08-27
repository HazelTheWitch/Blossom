package com.hazeltrinity.blossomtest;

import com.hazeltrinity.blossom.BMod;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlossomTestMod implements ModInitializer {
    public static final BMod MOD = new BMod("Blossom Test Mod", "blossomtest");

    public static final Identifier SSBLOCK;
    public static final Block SSBLOCK_BLOCK;
    public static final BlockItem SSBLOCK_BLOCK_ITEM;
    public static final BlockEntityType<TestBlockEntity> SSBLOCK_BLOCK_ENTITY;

    public static final ScreenHandlerType<TestScreenHandler> SSBLOCK_SCREEN_HANDLER;

    static {
        SSBLOCK = MOD.id("test_server");

        SSBLOCK_BLOCK = Registry.register(Registry.BLOCK, SSBLOCK, new TestBlock());
        SSBLOCK_BLOCK_ITEM = Registry.register(Registry.ITEM, SSBLOCK, new BlockItem(SSBLOCK_BLOCK, new Item.Settings()));
        SSBLOCK_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, SSBLOCK, BlockEntityType.Builder.create(TestBlockEntity::new, SSBLOCK_BLOCK).build(null));

        SSBLOCK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(SSBLOCK, TestScreenHandler::new);
    }

    @Override
    public void onInitialize() {

    }
}