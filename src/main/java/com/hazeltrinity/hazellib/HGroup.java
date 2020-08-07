package com.hazeltrinity.hazellib;

import java.util.function.Supplier;

import com.hazeltrinity.hazellib.init.HInitializable;
import com.hazeltrinity.hazellib.init.HMod;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HGroup extends HInitializable {
    public final String name;

    private HMod mod;

    private Identifier identifier;

    public HGroup(String name) {
        this.name = name;
    }

    public void setMod(HMod mod) {
        this.mod = mod;
    }

    public Identifier getIdentifier(HMod mod) {
        if (identifier == null) {
            identifier = new Identifier(mod.id, name);
        }

        return identifier;
    }

    @Nullable
    public Identifier getIdentifier() {
        return identifier;
    }

    // REGISTERED ITEMS
    // Block

    private Block block;
    
    public Block getBlock() {
        return block;
    }

    public HGroup setBlock(Block block) {
        this.block = block;
        return this;
    }

    // Item/BlockItem

    private Item item;

    public Item getItem() {
        return item;
    }

    public HGroup setBlockItem(Item.Settings settings) throws IllegalStateException {
        if (block == null) {
            throw new IllegalStateException();
        }

        return setItem(new BlockItem(block, settings));
    }

    public HGroup setItem(Item item) {
        this.item = item;
        return this;
    }

    // Entity Type

    private BlockEntityType<?> blockEntityType;

    public BlockEntityType<?> getBlockEntityType() {
        return blockEntityType;
    }

    public HGroup setBlockEntityType(Supplier<? extends BlockEntity> supplier) throws IllegalStateException {
        if (block == null) {
            throw new IllegalStateException();
        }

        blockEntityType = BlockEntityType.Builder.create(supplier, block).build(null);
        return this;
    }

    // Render Layer

    private RenderLayer renderLayer;

    public RenderLayer getRenderLayer() {
        return renderLayer;
    }

    public HGroup setRenderLayer(RenderLayer renderLayer) throws IllegalStateException {
        if (block == null) {
            throw new IllegalStateException();
        }

        this.renderLayer = renderLayer;
        return this;
    }

    // INITIALIZATION

    @Override
    public void onInitialize() {
        Identifier id = getIdentifier(mod);

        if (block != null) {
            Registry.register(Registry.BLOCK, id, block);
        }

        if (item != null) {
            Registry.register(Registry.ITEM, id, item);
        }

        if (blockEntityType != null) {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, id, blockEntityType);
        }
    }

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
    }

    @Override
    public void onInitializeServer() { }

    @Override
    public void onPreLaunch() { }
    
}