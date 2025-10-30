package net.vascorito;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.vascorito.render.CustomTridentEntityRenderer;

public class ModRenderers {
    public static void initialise() {
        EntityRendererRegistry.register(ModEntities.NETHERITE, ctx -> new CustomTridentEntityRenderer(ctx, "textures/entity/netherite.png"));
    }
}