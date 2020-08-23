package com.hazeltrinity.blossomtest;

import com.hazeltrinity.blossom.gui.drawing.VanillaBackgroundPainter;
import com.hazeltrinity.blossom.gui.screen.BDescription;
import com.hazeltrinity.blossom.gui.screen.BScreen;
import com.hazeltrinity.blossom.gui.widget.BWidget;
import com.hazeltrinity.blossom.gui.widget.impl.AbsolutePanel;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TestItem extends Item {

    public TestItem() {
        super(new Item.Settings().maxCount(1));
    }
    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient){
            MinecraftClient.getInstance().openScreen(new BScreen(
                new BDescription.Builder(
                    new AbsolutePanel()
                    .setName("Absolute Panel")
                    .setMinimumSize(new BWidget.Size(1, 100))
                    .setBackgroundPainter(new VanillaBackgroundPainter())
                )
                .build()
            ));
        }
        return super.use(world, user, hand);
    }
}