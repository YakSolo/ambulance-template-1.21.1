package net.yaksolo.ambulance.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ColorCode;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.yaksolo.ambulance.Ambulance;

public class ModBlocks {
    public static final Block RAINBOW_BLOCK = registerBlock("rainbow_block", new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN)));
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Ambulance.id(name), block);
    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Ambulance.id(name),
                new BlockItem(block, new Item.Settings()));
    }

    private static Block registerOnlyBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, Ambulance.id(name), block);
    }

    public static void registerModBlocks() {
        Ambulance.LOGGER.info("Registering Mod Blocks for " + Ambulance.MOD_ID);

    }
}
