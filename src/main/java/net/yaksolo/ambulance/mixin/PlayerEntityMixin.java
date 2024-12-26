package net.yaksolo.ambulance.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.entity.LivingEntity.getSlotForHand;

@Mixin(PlayerEntity.class)

public class PlayerEntityMixin {
    @Inject(method = {"damageShield"}, at = @At("HEAD"), cancellable = true)
    protected void damageShield(float amount, CallbackInfo info){
        info.cancel();

        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack activeItem = player.getActiveItem();
        ItemStack activeItemStack = player.getActiveItem();

        Object Item = null;
        if (activeItem.getItem() instanceof ShieldItem
        ) {
            if (!player.getWorld().isClient) {
                player.incrementStat(Stats.USED.getOrCreateStat(activeItem.getItem()));
            }

            if (amount >= 3.0F) {
                int i = 1 + MathHelper.floor(amount);
                Hand hand = player.getActiveHand();
                activeItem.damage(i, player, getSlotForHand(hand));
                if (activeItem.isEmpty()) {
                    if (hand == Hand.MAIN_HAND) {
                        player.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        player.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }

                    activeItem = ItemStack.EMPTY;
                    player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.getWorld().random.nextFloat() * 0.4F);
                }
            }
        }
    }

}
