package com.hazeltrinity.blossomtest;

import com.hazeltrinity.blossom.gui.BColor;
import com.hazeltrinity.blossom.gui.drawing.VanillaBackgroundPainter;
import com.hazeltrinity.blossom.gui.screen.BDescription;
import com.hazeltrinity.blossom.gui.screen.BScreenHandler;
import com.hazeltrinity.blossom.gui.screen.BSlot;
import com.hazeltrinity.blossom.gui.widget.impl.WAbsolutePanel;
import com.hazeltrinity.blossom.gui.widget.impl.WGridInventory;
import com.hazeltrinity.blossom.gui.widget.impl.WRect;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;

public class TestScreenHandler extends BScreenHandler {
    public TestScreenHandler(int syncId, PlayerInventory playerInventory, Inventory blockInventory) {
        super(BlossomTestMod.TestServerScreen.screenHandlerType(), syncId, playerInventory, blockInventory, new BDescription.Builder(
        new WAbsolutePanel()
        .addChild(
        new WRect()
        .setColor(BColor.FLATBE)
        .setMinimumSize(50, 50),
        0.25, 0,
        0.25, 0
        )
        .addChild(
        new WGridInventory()
        .setSlotGenerator((offset, n, m, playerInv, blockInv) -> {
            return new BSlot(blockInv, 0, 0, 0);
        }),
        0.0, 0,
        0.0, 0
        )
        .setMargins(10)
        .setName("Test Handled Screen")
        .setBackgroundPainter(new VanillaBackgroundPainter())
        ).build()
        );
    }

    public TestScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1));
    }
}
