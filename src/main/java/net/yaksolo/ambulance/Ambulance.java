package net.yaksolo.ambulance;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.yaksolo.ambulance.block.ModBlocks;
import net.yaksolo.ambulance.effects.ModEffects;
import net.yaksolo.ambulance.item.ModItemGroups;
import net.yaksolo.ambulance.item.ModItems;
import net.yaksolo.ambulance.misc.ModModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ambulance implements ModInitializer {
	public static final String MOD_ID = "ambulance";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModModifiers.changeItems();
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModEffects.reg();

	}
	public static Identifier id(String name){
		return Identifier.of(MOD_ID, name);
	}




}