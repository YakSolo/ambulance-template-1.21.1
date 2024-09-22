package net.yaksolo.ambulance.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.yaksolo.ambulance.Ambulance;
import net.yaksolo.ambulance.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup AMBULANCE = Registry.register(Registries.ITEM_GROUP, Ambulance.id("ambulance"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.ambulance"))
                    .icon(() -> new ItemStack(ModItems.RAINBOW_INGOT)).entries((displayContext, entries) -> {
                        entries.add(ModItems.STEAKSWORD);
                        entries.add(ModItems.RAINBOW_INGOT);

                        entries.add(ModBlocks.RAINBOW_BLOCK);
                    }).build());

public static void registerItemGroups() {
    Ambulance.LOGGER.info("Registering Item Groups for " + Ambulance.MOD_ID);

}
}
