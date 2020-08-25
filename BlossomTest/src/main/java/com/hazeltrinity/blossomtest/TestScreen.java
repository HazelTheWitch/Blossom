package com.hazeltrinity.blossomtest;

import com.hazeltrinity.blossom.gui.screen.BHandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class TestScreen extends BHandledScreen<TestScreenHandler> {
    public TestScreen(TestScreenHandler handler, PlayerInventory inventory, Text name) {
        super(handler, inventory, name);
    }
}
