package net.vascorito.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.vascorito.ModAttachments;
import net.vascorito.items.DamageTracking;

@Debug(export = true)
@Mixin(PlayerEntity.class)
abstract public class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;postHit(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/LivingEntity;)Z"))
    public void damageTracker(Entity target, CallbackInfo ci, @Local(ordinal = 3) float damage, @Local ItemStack item) {
        if (item.getItem() instanceof DamageTracking dtItem) {
            dtItem.afterHit(item, (LivingEntity) target, damage);
        }
    }
}
