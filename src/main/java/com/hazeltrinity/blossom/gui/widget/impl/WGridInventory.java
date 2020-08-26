package com.hazeltrinity.blossom.gui.widget.impl;

import com.hazeltrinity.blossom.gui.widget.BWidget;
import com.hazeltrinity.blossom.gui.widget.SlotProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;

/**
 * A widget which holds an NxM grid of slots, looks like a chest.
 */
public class WGridInventory extends BWidget implements SlotProvider {
    private List<Slot> slots;
    private int N = 1, M = 1;
    private SlotGenerator slotGenerator;

    public WGridInventory setSlotGenerator(SlotGenerator slotGenerator) {
        this.slotGenerator = slotGenerator;
        return this;
    }

    public WGridInventory setSize(int N, int M) {
        this.N = N;
        this.M = M;

        return this;
    }

    @Override
    public List<Slot> getSlots(int offset, PlayerInventory playerInventory, Inventory blockInventory) {
        ArrayList<Slot> slots = new ArrayList<Slot>();

        for (int n = 0; n < N; n++) {
            for (int m = 0; m < M; m++) {
                slots.add(slotGenerator.get(offset, n, m, playerInventory, blockInventory));
            }
        }

        return slots;
    }

    @FunctionalInterface
    public interface SlotGenerator {
        Slot get(int offset, int n, int m, PlayerInventory playerInventory, Inventory blockInventory);
    }
}
