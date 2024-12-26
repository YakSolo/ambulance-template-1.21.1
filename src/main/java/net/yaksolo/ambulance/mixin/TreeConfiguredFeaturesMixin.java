package net.yaksolo.ambulance.mixin;

import net.minecraft.block.*;
import net.minecraft.registry.Registerable;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.foliage.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalInt;

import static net.minecraft.world.gen.feature.TreeConfiguredFeatures.DARK_OAK;


@Mixin(TreeConfiguredFeatures.class)

public class TreeConfiguredFeaturesMixin {
   @Inject(method = {"bootstrap"}, at = @At("INVOKE"), cancellable = true)
    private static void bootstrap (Registerable<ConfiguredFeature<?, ?>> featureRegisterable, CallbackInfo info) {
       info.cancel();
       ConfiguredFeatures.register(
               featureRegisterable,
               DARK_OAK,
               Feature.TREE,
               new TreeFeatureConfig.Builder(
                       BlockStateProvider.of(Blocks.ACACIA_WOOD),
                       new DarkOakTrunkPlacer(26, 26, 16),
                       BlockStateProvider.of(Blocks.DARK_OAK_LEAVES),
                       new DarkOakFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
                       new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())
               )
                       .ignoreVines()
                       .build()
       );
   }
}
