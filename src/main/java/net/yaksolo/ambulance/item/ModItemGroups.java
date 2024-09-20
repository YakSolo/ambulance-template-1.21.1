package net.yaksolo.ambulance.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.yaksolo.ambulance.Ambulance;

import java.rmi.registry.Registry;

public class ModItemGroups {
    public static final ItemGroup AMBULANCE = Registry.register(Registries.ITEM_GROUP, Ambulance.id("ambulance"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.ambulance"))
                    .icon()) -> new ItemStack(ModItems.LGBTQBAR)).entries((displayContext,entries) -> {
                        entries.add(ModItems.LGBTQBAR);
                        entries.add(ModItems.STEAKSWORD);
    }).build());
public static void registerItemGroups() {
    Ambulance.LOGGER.info("Registering Item Groups for " + Ambulance.MOD_ID);

}
}
