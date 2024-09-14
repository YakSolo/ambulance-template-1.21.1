package net.yaksolo.ambulance.misc;

import net.minecraft.component.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Unit;

import java.lang.reflect.Field;

public class ModModifiers {

    public static void changeItems() {
        for (Item item : Registries.ITEM) {
            if (item == Items.TOTEM_OF_UNDYING) {
                ComponentMap cm = ComponentMapImpl.create(item.getComponents(), ComponentChanges.builder().add(DataComponentTypes.MAX_STACK_SIZE, 3).build());
                changeItem(item, cm);
            } else if (item == Items.SNOWBALL) {
                ComponentMap cm = ComponentMapImpl.create(item.getComponents(), ComponentChanges.builder().add(DataComponentTypes.MAX_STACK_SIZE, 32).build());
                changeItem(item, cm);
            } else if (item == Items.GHAST_TEAR) {
                ComponentMap cm = ComponentMapImpl.create(item.getComponents(), ComponentChanges.builder().add(DataComponentTypes.FIRE_RESISTANT, Unit.INSTANCE).build());
                changeItem(item, cm);
            } else if (item == Items.TRIDENT) {
                ComponentMap cm = ComponentMapImpl.create(item.getComponents(), ComponentChanges.builder().add(DataComponentTypes.MAX_DAMAGE, 2000).build());
                changeItem(item, cm);
            }
        }
    }

    static void changeItem(Object obj, Object value) {
        try {
            Field field;
            field = Item.class.getDeclaredField("components");
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception ignored) {
        }
    }

}
