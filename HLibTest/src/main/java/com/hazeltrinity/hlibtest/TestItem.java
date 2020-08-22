package com.hazeltrinity.hlibtest;

import com.hazeltrinity.hazellib.gui.drawing.VanillaBackgroundPainter;
import com.hazeltrinity.hazellib.gui.screen.HDescription;
import com.hazeltrinity.hazellib.gui.screen.HScreen;
import com.hazeltrinity.hazellib.gui.widget.HWidget;
import com.hazeltrinity.hazellib.gui.widget.impl.AbsolutePanel;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
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
            MinecraftClient.getInstance().openScreen(new HScreen(
                new HDescription.Builder(
                    new AbsolutePanel(new HWidget.Size(1, 100), new LiteralText("Absolute Panel"))
                    .setBackgroundPainter(new VanillaBackgroundPainter())
                )
                .build()
            ));
        }
        return super.use(world, user, hand);
    }
}