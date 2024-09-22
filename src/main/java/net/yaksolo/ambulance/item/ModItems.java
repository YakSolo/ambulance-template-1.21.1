package net.yaksolo.ambulance.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.yaksolo.ambulance.Ambulance;


public class ModItems {
    public static final Item STEAKSWORD = registerItem("steaksword", new Steaksword(new Item.Settings().maxCount(1).maxDamage(25565)
            .attributeModifiers(Steaksword.createAttributeModifiers())
            .component(DataComponentTypes.TOOL, Steaksword.createToolComponent())));
    public static final Item RAINBOW_INGOT = registerItem("rainbow_ingot", new Item(new Item.Settings()));

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {

    }
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Ambulance.id(name), item);
    }

    private static Item registerVanillaItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.ofVanilla(name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }//
}
