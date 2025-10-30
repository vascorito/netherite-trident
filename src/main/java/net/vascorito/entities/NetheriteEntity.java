package net.vascorito.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.vascorito.ModEntities;
import net.vascorito.other.TridentEntityDuck;

public class NetheriteEntity extends CustomTridentEntity<NetheriteEntity> {
    public NetheriteEntity(EntityType<? extends NetheriteEntity> type, World world) {
        super(type, world);
    }

    public NetheriteEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.NETHERITE, world, owner, stack);
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
    }

    @Override
    public void tick() {
        super.tick();
        if (!((TridentEntityDuck) this).tritastic$getDealtDamage()) {
            var pos = this.getEntityPos();
            if (this.getOwner() instanceof PlayerEntity player) {
                if (player.getEntityWorld().getRegistryKey().equals(World.NETHER)) {
                    this.getEntityWorld().addParticleClient(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 0, 0, 0);
                } else {
                    this.getEntityWorld().addParticleClient(ParticleTypes.FALLING_WATER, pos.x, pos.y, pos.z, 0, 0, 0);
                }
            }
        }
    }
}