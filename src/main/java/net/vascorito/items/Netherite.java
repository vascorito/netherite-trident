package net.vascorito.items;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import net.vascorito.ModItems;
import net.vascorito.NetheriteTrident;
import net.vascorito.entities.NetheriteEntity;

import java.util.function.Consumer;

public class Netherite extends CustomTrident<NetheriteEntity> {
    public Netherite(Item.Settings settings) {
        super(settings, NetheriteTrident.id("textures/entity/netherite.png"));
    }

    @Override
    public boolean riptideCondition(PlayerEntity player, ItemStack item) {
        return player.getMainHandStack().isOf(ModItems.NETHERITE);
    }

    @Override
    public ProjectileEntity.@NotNull ProjectileCreator<NetheriteEntity> newProjectile() {
        return NetheriteEntity::new;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> tooltip, TooltipType type) {
        CustomTrident.tooltip("netherite").forEach(tooltip);
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
    }
}
