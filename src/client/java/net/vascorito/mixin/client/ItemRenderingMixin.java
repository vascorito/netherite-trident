package net.vascorito.mixin.client;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DrawContext.class)
public abstract class ItemRenderingMixin {
    @Shadow public abstract void fill(RenderPipeline pipeline, int x1, int y1, int x2, int y2, int z);
}