package com.hazeltrinity.blossom.init;

import java.util.function.Supplier;

import com.hazeltrinity.blossom.BMod;

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

public class BGroup extends BInitializable {
    public final String name;

    private BMod mod;

    private Identifier identifier;

    /**
     * Construct a new Group of items, all share the same {@code Identifier}.
     * 
     * @param name the name of the identifier
     */
    public BGroup(String name) {
        this.name = name;
    }

    /**
     * Construct a new Group of items, all share the same {@code Identifier}, and
     * set the mod to {@code mod}.
     * 
     * @param name the name of the identifier
     * @param mod  the mod to use
     */
    public BGroup(String name, BMod mod) {
        this(name);

        setMod(mod);
    }

    /**
     * Set this group's mod to {@code mod}.
     * 
     * @param mod the mod to set
     */
    public void setMod(BMod mod) {
        this.mod = mod;
    }

    /**
     * Get this group's identifier given the mod set with {@code setMod}.
     * 
     * @return the identifier of the group or null if {@code mod} has not been set
     */
    @Nullable
    public Identifier getIdentifier() {
        if (identifier == null && mod != null) {
            identifier = new Identifier(mod.id, name);
        }

        return identifier;
    }

    // REGISTERED ITEMS
    // Block

    private Block block;
    
    @Nullable
    public Block getBlock() {
        return block;
    }

    /**
     * Set this group's block.
     * 
     * @param block the block to set
     * @return this group for chaining
     * @throws IllegalStateException if called multiple times
     */
    public BGroup setBlock(Block block) throws IllegalStateException {
        if (block != null) {
            throw new IllegalStateException("Multiple blocks attempted to be set in " + name + ".");
        }
        this.block = block;
        return this;
    }

    // Item/BlockItem

    private Item item;

    @Nullable
    public Item getItem() {
        return item;
    }

    /**
     * Set this group's block item.
     * 
     * @param settings the setting to use in creating the block item
     * @return this group for chaining
     * @throws IllegalStateException if called multiple times, called as well as
     *                               {@code setItem}, or before {@code setBlock}
     */
    public BGroup setBlockItem(Item.Settings settings) throws IllegalStateException {
        if (block == null) {
            throw new IllegalStateException("No block has been set in " + name + ".");
        }

        return setItem(new BlockItem(block, settings));
    }

    /**
     * Set this group's item.
     * 
     * @param item the item to set
     * @return this group for chaining
     * @throws IllegalStateException if called multiple or with {@code setBlockItem}
     */
    public BGroup setItem(Item item) throws IllegalStateException {
        if (this.item != null) {
            throw new IllegalStateException("Multiple items attempted to be set in " + name + ".");
        }
        this.item = item;
        return this;
    }

    // Entity Type

    private BlockEntityType<?> blockEntityType;

    @Nullable
    public BlockEntityType<?> getBlockEntityType() {
        return blockEntityType;
    }

    /**
     * Set this group's block entity type
     * 
     * @param supplier a supplier providing a block entity
     * @return this group for chaining
     * @throws IllegalStateException if called multiple times or no block has been
     *                               set
     */
    public BGroup setBlockEntityType(Supplier<? extends BlockEntity> supplier) throws IllegalStateException {
        if (blockEntityType != null) {
            throw new IllegalStateException("Multiple block entity types attempted to be set in " + name + ".");
        }

        if (block == null) {
            throw new IllegalStateException("No block has been set in " + name + ".");
        }

        blockEntityType = BlockEntityType.Builder.create(supplier, block).build(null);
        return this;
    }

    // Render Layer

    private RenderLayer renderLayer;

    @Nullable
    public RenderLayer getRenderLayer() {
        return renderLayer;
    }

    /**
     * Set this group's render layer
     * 
     * @param renderLayer the render layer to assign the block to
     * @return this group for chaining
     * @throws IllegalStateException if called multiple times or no block has been
     *                               set
     */
    public BGroup setRenderLayer(RenderLayer renderLayer) throws IllegalStateException {
        if (renderLayer != null) {
            throw new IllegalStateException("Multiple render layers attempted to be set in " + name + ".");
        }

        if (block == null) {
            throw new IllegalStateException("No block has been set in " + name + ".");
        }

        this.renderLayer = renderLayer;
        return this;
    }

    // INITIALIZATION

    @Override
    public void onInitialize() {
        getIdentifier();

        if (block != null) {
            Registry.register(Registry.BLOCK, identifier, block);
        }

        if (item != null) {
            Registry.register(Registry.ITEM, identifier, item);
        }

        if (blockEntityType != null) {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, identifier, blockEntityType);
        }
    }

    @Override
    public void onInitializeClient() {
        if (renderLayer != null) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
        }
    }

    @Override
    public void onInitializeServer() { }
}