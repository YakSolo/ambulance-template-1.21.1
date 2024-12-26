package net.yaksolo.ambulance.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;

import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BucklerItem extends ShieldItem {
    public BucklerItem(Settings settings) {
        super(settings);
    }
}