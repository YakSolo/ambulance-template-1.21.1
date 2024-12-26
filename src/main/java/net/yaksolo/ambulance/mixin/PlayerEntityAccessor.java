package net.yaksolo.ambulance.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerEntity.class)
public interface PlayerEntityAccessor {

    @Accessor("abilityResyncCountdown")
    int getAbilityResyncCountDown();

    @Accessor("abilityResyncCountdown")
    void setAbilityResyncCountDown(int abilityResyncCountDown);

}
