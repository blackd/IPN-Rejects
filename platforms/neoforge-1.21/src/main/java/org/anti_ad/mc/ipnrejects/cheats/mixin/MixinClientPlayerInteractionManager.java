package org.anti_ad.mc.ipnrejects.cheats.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.anti_ad.mc.ipnrejects.cheats.config.Cheats;
import org.anti_ad.mc.ipnrejects.config.ModSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * MixinClientPlayerInteractionManager
 */
@Mixin(MultiPlayerGameMode.class)
public class MixinClientPlayerInteractionManager {

    @Shadow
    private int destroyDelay;

    @Inject(at = @At("HEAD"), method = "continueDestroyBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z")
    public void updateBlockBreakingProgress(BlockPos p_105284_, Direction p_105285_, CallbackInfoReturnable<Boolean> cir) {
        if (this.destroyDelay > 0 && ModSettings.INSTANCE.getENABLE_CHEATS().getValue() && Cheats.INSTANCE.getDISABLE_BLOCK_BREAKING_COOLDOWN().getBooleanValue()) {
            this.destroyDelay = 0;
        }
    }




    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;destroyBlock(Lnet/minecraft/core/BlockPos;)Z"),
            method = "lambda$startDestroyBlock$1(Lnet/minecraft/world/level/block/state/BlockState;Lnet/neoforged/neoforge/event/entity/player/PlayerInteractEvent$LeftClickBlock;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;I)Lnet/minecraft/network/protocol/Packet;")
    public void attackBlock(BlockState blockstate1, PlayerInteractEvent.LeftClickBlock event, BlockPos p_105270_, Direction p_105271_, int p_233728_, CallbackInfoReturnable<Packet> cir) {
        if (ModSettings.INSTANCE.getENABLE_CHEATS().getValue() && Cheats.INSTANCE.getINSTANT_MINING_COOLDOWN().getBooleanValue()) {
            this.destroyDelay = 5;
        }
    }

}
