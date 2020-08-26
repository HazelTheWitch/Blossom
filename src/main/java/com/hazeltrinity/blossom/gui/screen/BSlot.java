package com.hazeltrinity.blossom.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

/**
 * A Blossom slot for use with {@link com.hazeltrinity.blossom.gui.widget.SlotProvider}
 */
public class BSlot extends Slot {
    /**
     * Create a new slot for a given inventory, index, and position.
     *
     * @param inventory the inventory this slot belongs to
     * @param index     the index of the inventory
     * @param x         the x position
     * @param y         the y position
     */
    public BSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean doDrawHoveringEffect() {
        return true;
    }
}
