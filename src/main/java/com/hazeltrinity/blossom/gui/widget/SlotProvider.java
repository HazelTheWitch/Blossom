package com.hazeltrinity.blossom.gui.widget;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;

public interface SlotProvider {
    /**
     * Get a list of slots this widget provides
     *
     * @return a list of all slots in this widget
     */
    default List<Slot> getSlots(int offset, PlayerInventory playerInventory, Inventory blockInventory) {
        return new ArrayList<Slot>();
    }
}

