package net.vascorito;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ExplosionLargeParticle;
import net.minecraft.item.Items;

public class NetheriteTridentClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModRenderers.initialise();
		ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
			if (itemStack.getItem().equals(Items.TRIDENT)) {
				list.addAll(1, net.vascorito.items.CustomTrident.tooltip("trident"));
			}
		});

		net.vascorito.other.ExpandingTooltip.INSTANCE = MinecraftClient.getInstance()::isShiftPressed;
	}
}