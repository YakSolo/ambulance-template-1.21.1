package net.yaksolo.ambulance.item;

import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
        return new ToolComponent(List.of(), 100.0F, 1);
        }

        @Override
        public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
                return !miner.isCreative();
        }

        @Override
        public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                return true;
        }

        @Override
        public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        }


}
