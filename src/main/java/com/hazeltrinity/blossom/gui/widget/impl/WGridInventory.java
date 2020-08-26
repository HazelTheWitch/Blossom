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

    /**
     * Set the slot generator for this widget
     *
     * @param slotGenerator the slot generator
     *
     * @return this for fluent chaining
     */
    public WGridInventory setSlotGenerator(SlotGenerator slotGenerator) {
        this.slotGenerator = slotGenerator;
        return this;
    }

    /**
     * Set the size of the grid of this widget
     *
     * @param N the width of the grid
     * @param M the height of the grid
     *
     * @return this for fluent chaining
     */
    public WGridInventory setSize(int N, int M) {
        this.N = N;
        this.M = M;

        return this;
    }

    @Override
    public List<Slot> getSlots(PlayerInventory playerInventory, Inventory blockInventory) {
        ArrayList<Slot> slots = new ArrayList<Slot>();

        int index = 0;

        for (int n = 0; n < N; n++) {
            for (int m = 0; m < M; m++) {
                slots.add(slotGenerator.get(index, n, m, playerInventory, blockInventory));
                index++;
            }
        }

        return slots;
    }

    /**
     * A functional interface which generates each slot.
     */
    @FunctionalInterface
    public interface SlotGenerator {
        /**
         * Get a slot.
         *
         * @param index           the index of this slot, 0 is at (0, 0) in the grid
         * @param n               the x coordinate in the grid
         * @param m               the y coordinate in the grid
         * @param playerInventory the player's inventory
         * @param blockInventory  the block's inventory
         *
         * @return the new {@link Slot}
         */
        Slot get(int index, int n, int m, PlayerInventory playerInventory, Inventory blockInventory);
    }
}
