package net.yaksolo.ambulance.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Colors;

public class FruitExtensionEffect extends StatusEffect {
    protected FruitExtensionEffect() {
        super(StatusEffectCategory.BENEFICIAL, Colors.GREEN);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity.getHealth() < entity.getMaxHealth() && entity.age % 20 == 0) {
            entity.heal(1.0F);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onEntityRemoval(LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
        if (entity.getHealth() > entity.getMaxHealth()) {
            entity.setHealth(entity.getMaxHealth());
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
