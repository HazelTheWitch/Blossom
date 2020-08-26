package com.hazeltrinity.blossom.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

public class BSlot extends Slot {
    public BSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean doDrawHoveringEffect() {
        return true;
    }
}
