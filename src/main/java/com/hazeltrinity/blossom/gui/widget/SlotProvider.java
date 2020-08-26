package com.hazeltrinity.blossom.gui.widget;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a widget that contains slots.
 */
public interface SlotProvider {
    /**
     * Get a list of slots this widget provides.
     *
     * @param playerInventory the player inventory
     * @param blockInventory  the block inventory
     *
     * @return a list of all slots in this widget
     */
    default List<Slot> getSlots(PlayerInventory playerInventory, Inventory blockInventory) {
        return new ArrayList<Slot>();
    }
}

