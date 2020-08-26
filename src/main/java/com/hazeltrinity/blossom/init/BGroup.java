package com.hazeltrinity.blossom.init;

import com.hazeltrinity.blossom.BMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry.Factory;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.ExtendedClientHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.SimpleClientHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * A group of objects to be initialized. They are each handled at the right time automatically. Each item shares the
 * same identifier.
 */
public class BGroup extends BInitializable {

    public final String name;

    private BMod mod;

    private Identifier identifier;
    private Block block;
    private Item item;
    private BlockEntityType<?> blockEntityType;
    // Render Layer
    @Environment(EnvType.CLIENT)
    private RenderLayer renderLayer;

    // REGISTERED ITEMS
    // Block
    private BScreenGroup<?, ?, ?> screenGroup;

    /**
     * Construct a new Group of items, all share the same {@code Identifier}.
     *
     * @param name the name of the identifier
     */
    public BGroup(String name) {
        this.name = name;
    }

    /**
     * Construct a new Group of items, all share the same {@code Identifier}, and set the mod to {@code mod}.
     *
     * @param name the name of the identifier
     * @param mod  the mod to use
     */
    public BGroup(String name, BMod mod) {
        this(name);

        setMod(mod);
    }

    // Item/BlockItem

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
            identifier = mod.id(name);
        }

        return identifier;
    }

    /**
     * Get the block of this group.
     *
     * @return the block
     */
    @Nullable
    public Block getBlock() {
        return block;
    }

    /**
     * Set this group's block.
     *
     * @param block the block to set
     *
     * @return this group for chaining
     *
     * @throws IllegalStateException if called multiple times
     */
    public BGroup setBlock(Block block) throws IllegalStateException {
        if (this.block != null) {
            throw new IllegalStateException("Multiple blocks attempted to be set in " + name + ".");
        }
        this.block = block;
        return this;
    }

    // Entity Type

    /**
     * Get the item of this group.
     *
     * @return the item
     */
    @Nullable
    public Item getItem() {
        return item;
    }

    /**
     * Set this group's item.
     *
     * @param item the item to set
     *
     * @return this group for chaining
     *
     * @throws IllegalStateException if called multiple or with {@code setBlockItem}
     */
    public BGroup setItem(Item item) throws IllegalStateException {
        if (this.item != null) {
            throw new IllegalStateException("Multiple items attempted to be set in " + name + ".");
        }
        this.item = item;
        return this;
    }

    /**
     * Set this group's block item.
     *
     * @param settings the setting to use in creating the block item
     *
     * @return this group for chaining
     *
     * @throws IllegalStateException if called multiple times, called as well as {@code setItem}, or before {@code
     *                               setBlock}
     */
    public BGroup setBlockItem(Item.Settings settings) throws IllegalStateException {
        if (this.block == null) {
            throw new IllegalStateException("No block has been set in " + name + ".");
        }

        return setItem(new BlockItem(block, settings));
    }

    /**
     * Get the block entity type of this group.
     *
     * @return the block entity type
     */
    @Nullable
    public BlockEntityType<?> getBlockEntityType() {
        return blockEntityType;
    }

    /**
     * Set this group's block entity type
     *
     * @param supplier a supplier providing a block entity
     *
     * @return this group for chaining
     *
     * @throws IllegalStateException if called multiple times or no block has been set
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

    /**
     * Get the block's render layer.
     *
     * @return the block's render layer
     */
    @Environment(EnvType.CLIENT)
    @Nullable
    public RenderLayer getRenderLayer() {
        return renderLayer;
    }

    // Screen Handler

    /**
     * Set this group's render layer
     *
     * @param renderLayer the render layer to assign the block to
     *
     * @return this group for chaining
     *
     * @throws IllegalStateException if called multiple times or no block has been set
     */
    @Environment(EnvType.CLIENT)
    public BGroup setRenderLayer(RenderLayer renderLayer) throws IllegalStateException {
        if (this.renderLayer != null) {
            throw new IllegalStateException("Multiple render layers attempted to be set in " + name + ".");
        }

        if (block == null) {
            throw new IllegalStateException("No block has been set in " + name + ".");
        }

        this.renderLayer = renderLayer;
        return this;
    }

    /**
     * Set this group's screen handler and screen for simple screens
     *
     * @param screenHandler the screen handler factory
     * @param screen        the screen factory
     * @param <H>           the screenhandler type
     * @param <S>           the screen type
     *
     * @return this for chaining
     */
    public <H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>> BGroup setScreenHandlerSimple(SimpleClientHandlerFactory<H> screenHandler, Factory<H, S> screen) {
        screenGroup = new BScreenGroup<H, H, S>(identifier).setScreenHandlerSimple(screenHandler).setScreen(screen);
        tackOn(screenGroup);
        return this;
    }

    /**
     * Set this group's screen handler and screen for extended screens
     *
     * @param screenHandler the screen handler factory
     * @param screen        the screen factory
     * @param <H>           the screenhandler type
     * @param <S>           the screen type
     *
     * @return this for chaining
     */
    public <H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>> BGroup setScreenHandlerExtended(ExtendedClientHandlerFactory<H> screenHandler, Factory<H, S> screen) {
        screenGroup = new BScreenGroup<H, H, S>(identifier).setScreenHandlerExtended(screenHandler).setScreen(screen);
        tackOn(screenGroup);
        return this;
    }

    /**
     * Get the screen handler type
     *
     * @return the screen handler type
     */
    public ScreenHandlerType<?> screenHandlerType() {
        return screenGroup.getScreenHandlerType();
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

        super.onInitialize();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        if (renderLayer != null) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
        }

        super.onInitializeClient();
    }

    @Override
    @Environment(EnvType.SERVER)
    public void onInitializeServer() {
        super.onInitializeServer();
    }
}