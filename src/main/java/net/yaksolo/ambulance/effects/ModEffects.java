package net.yaksolo.ambulance.effects;

import net.minecraft.block.IceBlock;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.yaksolo.ambulance.Ambulance;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> FRUIT_EXTENSION = register("fruit_extension", new FruitExtensionEffect().addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, Ambulance.id("effect.fruit_extension"), 30, EntityAttributeModifier.Operation.ADD_VALUE));

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Ambulance.id(id), statusEffect);
    }

    public static void reg(){
        Ambulance.LOGGER.info("Registered effects");
    }

}
