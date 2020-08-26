package com.hazeltrinity.blossom.gui.screen;

import com.hazeltrinity.blossom.gui.widget.BWidget;
import com.hazeltrinity.blossom.gui.widget.SlotProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * A Blossom Screen handler.
 */
public class BScreenHandler extends ScreenHandler {

    /**
     * Tells the click handler to default to super implementation of whichever action.
     */
    public static final SlotClickAction NULL_ACTION = (handler, slotNumber, button, player) -> null;
    public final BDescription description;
    public final PlayerInventory playerInventory;
    public final @Nullable Inventory blockInventory;
    private final HashMap<SlotActionType, SlotClickAction> actionCases = new HashMap<>();

    /**
     * Create a new BScreenHandler with no block inventory.
     *
     * @param type            the screen handler type
     * @param syncId          the sync id
     * @param playerInventory the player inventory
     * @param description     the {@link BDescription} to use
     */
    public BScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, BDescription description) {
        super(type, syncId);

        this.description = description;

        this.playerInventory = playerInventory;
        this.blockInventory = null;
    }

    /**
     * Create a new BScreenHandler with a block inventory.
     *
     * @param type            the screen handler type
     * @param syncId          the sync id
     * @param playerInventory the player inventory
     * @param blockInventory  the block inventory to use
     * @param description     the {@link BDescription} to use
     */
    public BScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory blockInventory, BDescription description) {
        super(type, syncId);

        this.description = description;

        this.playerInventory = playerInventory;
        this.blockInventory = blockInventory;

        blockInventory.onOpen(playerInventory.player);

        initSlots();
    }

    /**
     * Define the action to take in different cases.
     *
     * @param type   which case to be used
     * @param action the action to take
     *
     * @return this for chaining
     */
    public BScreenHandler setSlotClickCase(SlotActionType type, SlotClickAction action) {
        actionCases.put(type, action);
        return this;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        if (blockInventory == null) {
            return false;
        }

        return blockInventory.canPlayerUse(player);

    }

    @Override
    public ItemStack onSlotClick(int slotNumber, int button, SlotActionType action, PlayerEntity player) {
        SlotClickAction actionCase = actionCases.getOrDefault(action, NULL_ACTION);

        ItemStack result = actionCase.act(this, slotNumber, button, player);

        if (result != null) {
            return result;
        }

        return super.onSlotClick(slotNumber, button, action, player);
    }

    private void initSlots() {
        for (BWidget widget : description.root.getAncestors()) {
            if (widget instanceof SlotProvider) {
                for (Slot slot : ((SlotProvider) widget).getSlots(playerInventory, blockInventory)) {
                    super.addSlot(slot);
                }
            }
        }
    }

    /**
     * Represents an action that takes place when a slot is clicked.
     */
    @FunctionalInterface
    public interface SlotClickAction {
        /**
         * The action taken when triggered
         *
         * @param handler    the screen handler
         * @param slotNumber the slot number
         * @param button     the button value
         * @param player     the player
         *
         * @return the {@link ItemStack} in the slot
         */
        @Nullable ItemStack act(BScreenHandler handler, int slotNumber, int button, PlayerEntity player);
    }
}
