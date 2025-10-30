package net.vascorito.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.TridentItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.vascorito.ModItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityNetheriteMixin extends LivingEntity {

    @Shadow
    public abstract ItemCooldownManager getItemCooldownManager();

    protected PlayerEntityNetheriteMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {

        if (this.getMainHandStack().getItem() == ModItems.NETHERITE && this.isTouchingWaterOrRain())
        {
            this.getItemCooldownManager().remove(this.getItemCooldownManager().getGroup(this.getMainHandStack()));
        }
    }

    // Map to store the positions of lava blocks transformed into ice
    @Unique
    private static final Map<BlockPos, BlockPos> LavaToIceMap = new HashMap<>();

    @Inject(method = "tick", at = @At("HEAD"))
    private void transformLavaToIce(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        World world = player.getEntityWorld();

        // Check if the player is holding a trident enchanted with Riptide
        if (player.getMainHandStack().getItem() instanceof TridentItem &&
                EnchantmentHelper.getTridentSpinAttackStrength(player.getMainHandStack(), player) > 0.0F) {

            // Check if the player is in lava
            if (player.isInLava()) {
                BlockPos blockPos = player.getBlockPos().down();

                // Check if the position is valid and if the block is lava
                if (world != null && world.getBlockState(blockPos).isOf(Blocks.LAVA)) {
                    world.setBlockState(blockPos, Blocks.ICE.getDefaultState());

                    LavaToIceMap.put(blockPos, blockPos);

                    world.playSound(null, blockPos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    if (world instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.LARGE_SMOKE,
                                blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5,
                                10, 0.1, 0.1, 0.1, 0.05);
                    }
                }
            } else {
                // Check if the player is about to fall into lava
                BlockPos blockPos = player.getBlockPos().down();

                // Check if the position is valid and if the block is lava
                if (world != null && world.getBlockState(blockPos).isOf(Blocks.LAVA)) {
                    world.setBlockState(blockPos, Blocks.ICE.getDefaultState());

                    LavaToIceMap.put(blockPos, blockPos);

                    world.playSound(null, blockPos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    if (world instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.LARGE_SMOKE,
                                blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5,
                                10, 0.1, 0.1, 0.1, 0.05);
                    }
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void transformMeltedIceToLava(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        World world = player.getEntityWorld();

        if (world != null && !LavaToIceMap.isEmpty()) {
            // List to store the keys to be removed
            List<BlockPos> positionsToRemove = new ArrayList<>();

            for (Map.Entry<BlockPos, BlockPos> entry : LavaToIceMap.entrySet()) {
                BlockPos icePos = entry.getKey(); // Position of the ice block
                BlockPos originalLavaPos = entry.getValue(); // Original position of the lava

                // Check if the ice block has melted
                if (!world.getBlockState(icePos).isOf(Blocks.ICE)) {
                    // Replace the ice back to lava at the original position
                    world.setBlockState(originalLavaPos, Blocks.LAVA.getDefaultState());

                    // Add the key to the removal list
                    positionsToRemove.add(icePos);

                    // Sound and particles to visual effect
                    world.playSound(null, originalLavaPos, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    if (world instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(ParticleTypes.LAVA,
                                originalLavaPos.getX() + 0.5, originalLavaPos.getY() + 1, originalLavaPos.getZ() + 0.5,
                                10, 0.1, 0.1, 0.1, 0.05);
                    }
                }
            }

            // After iteration, remove the map entries
            for (BlockPos icePos : positionsToRemove) {
                LavaToIceMap.remove(icePos);
            }
        }
    }
}