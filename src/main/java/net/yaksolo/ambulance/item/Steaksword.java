package net.yaksolo.ambulance.item;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;


public class Steaksword extends Item {
        public Steaksword (Item.Settings settings) {
                super(settings);
        }
        public static AttributeModifiersComponent createAttributeModifiers() {
                return AttributeModifiersComponent.builder()
                        .add(
                                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                                new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 100.0, EntityAttributeModifier.Operation.ADD_VALUE),
                                AttributeModifierSlot.MAINHAND
                        )
                        .add(
                                EntityAttributes.GENERIC_ATTACK_SPEED,
                                new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -0F, EntityAttributeModifier.Operation.ADD_VALUE),
                                AttributeModifierSlot.MAINHAND
                        )
                        .build();
        }
        public static ToolComponent createToolComponent() {
        return new ToolComponent(List.of(), 1.0F, 999999);
        }

}
