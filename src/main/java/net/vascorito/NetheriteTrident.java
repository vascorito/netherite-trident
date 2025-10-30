package net.vascorito;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetheriteTrident implements ModInitializer {
	public static final String MOD_ID = "netherite-trident";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Identifier id(String str) {
		return Identifier.of(MOD_ID, str);
	}

	@Override
	public void onInitialize() {

		ModItems.initialise();
		ModEntities.initialise();
		ModAttachments.initialise();

		net.vascorito.other.ExpandingTooltip.INSTANCE = () -> false;
	}
}