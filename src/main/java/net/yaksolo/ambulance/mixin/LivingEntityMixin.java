package net.yaksolo.ambulance.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = {"damage"}, at = @At("INVOKE"))
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        LivingEntity e = (LivingEntity) (Object) this;
        if(e.getAttacker() != null){
            for(LivingEntity le : e.getWorld().getEntitiesByClass(LivingEntity.class, e.getBoundingBox().expand(16,16,16), LivingEntity::isAlive)){
                le.setAttacker(e.getAttacker());
            }
        }
    }

}
