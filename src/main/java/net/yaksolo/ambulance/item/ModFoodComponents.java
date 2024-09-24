package net.yaksolo.ambulance.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.ModStatus;
import net.yaksolo.ambulance.effects.ModEffects;

public class ModFoodComponents {
    public static final FoodComponent LIFEOFRUIT = new FoodComponent.Builder().nutrition(20).saturationModifier(20f)
            .statusEffect(new StatusEffectInstance(ModEffects.FRUIT_EXTENSION, 72000), 1f).build();
}
