package net.yaksolo.ambulance.mixin;

import com.sun.jdi.Method;
import com.sun.jdi.event.MethodEntryEvent;
import net.fabricmc.loader.impl.lib.mappingio.tree.MappingTree;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.apache.commons.compress.harmony.unpack200.bytecode.forms.IMethodRefForm;
import org.apache.commons.lang3.function.MethodInvokers;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.code.MethodSlice;
import org.spongepowered.asm.mixin.injection.code.MethodSlices;
import org.spongepowered.asm.mixin.injection.points.MethodHead;
import org.spongepowered.asm.mixin.struct.MemberRef;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;

import java.beans.MethodDescriptor;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

@Mixin(SnowGolemEntity.class)

public class SnowGolemEntityMixin extends GolemEntity {

    protected SnowGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = {"tickMovement"}, at = @At("INVOKE"), cancellable = true)
    public void tickMovement (CallbackInfo info){
        info.cancel();
        SnowGolemEntity entity = (SnowGolemEntity) (Object) this;
        super.tickMovement();
        if (!entity.getWorld().isClient) {
            if (entity.getWorld().getBiome(entity.getBlockPos()).isIn(BiomeTags.SNOW_GOLEM_MELTS)) {
                entity.damage(entity.getDamageSources().onFire(), 1.0F);
            }

            if (!entity.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                return;
            }

            BlockState blockState = Blocks.FIRE.getDefaultState();

            for (int i = 0; i < 4; i++) {
                int j = MathHelper.floor(entity.getX() + (double)((float)(i % 2 * 2 - 1) * 0.25F));
                int k = MathHelper.floor(entity.getY());
                int l = MathHelper.floor(entity.getZ() + (double)((float)(i / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockPos = new BlockPos(j, k, l);
                if (entity.getWorld().getBlockState(blockPos).isAir() && blockState.canPlaceAt(entity.getWorld(), blockPos)) {
                    entity.getWorld().setBlockState(blockPos, blockState);
                    entity.getWorld().emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(entity, blockState));
                }
            }
        }
    }

}
