package net.yaksolo.ambulance.mixin.client;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InGameHud.class)
public interface GameHudAccessor {

    @Accessor("random")
    Random getRandom();

}
