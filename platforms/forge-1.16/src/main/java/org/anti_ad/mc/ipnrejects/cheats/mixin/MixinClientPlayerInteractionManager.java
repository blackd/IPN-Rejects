package org.anti_ad.mc.ipnrejects.cheats.mixin;

import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
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
@Mixin(PlayerController.class)
public class MixinClientPlayerInteractionManager {

    @Shadow
    private int blockHitDelay;

    @Inject(at = @At("HEAD"), method = "onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Direction;)Z")
    public void updateBlockBreakingProgress(BlockPos blockPos_1, Direction direction_1, CallbackInfoReturnable<Boolean> info) {
        if (this.blockHitDelay > 0 && ModSettings.INSTANCE.getENABLE_CHEATS().getValue() && Cheats.INSTANCE.getDISABLE_BLOCK_BREAKING_COOLDOWN().getBooleanValue()) {
            this.blockHitDelay = 0;
        }
    }

    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/multiplayer/PlayerController;onPlayerDestroyBlock(Lnet/minecraft/util/math/BlockPos;)Z"),
            method = "clickBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/Direction;)Z")
    public void attackBlock(BlockPos blockPos_1, Direction direction_1, CallbackInfoReturnable<Boolean> info) {
        if (ModSettings.INSTANCE.getENABLE_CHEATS().getValue() && Cheats.INSTANCE.getINSTANT_MINING_COOLDOWN().getBooleanValue()) {
            this.blockHitDelay = 5;
        }
    }

}
