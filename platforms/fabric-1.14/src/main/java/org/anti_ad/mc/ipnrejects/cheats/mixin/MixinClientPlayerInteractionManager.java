package org.anti_ad.mc.ipnrejects.cheats.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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
@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {

    @Shadow
    private int field_3716;

    @Inject(at = @At("HEAD"), method = "method_2902(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z")
    public void updateBlockBreakingProgress(BlockPos blockPos_1, Direction direction_1, CallbackInfoReturnable<Boolean> info) {
        if (this.field_3716 > 0 && ModSettings.INSTANCE.getENABLE_CHEATS().getValue() && Cheats.INSTANCE.getDISABLE_BLOCK_BREAKING_COOLDOWN().getBooleanValue()) {
            this.field_3716 = 0;
        }
    }

    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;"
            + "breakBlock(Lnet/minecraft/util/math/BlockPos;)Z"),
            method = "attackBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z")
    public void attackBlock(BlockPos blockPos_1, Direction direction_1, CallbackInfoReturnable<Boolean> info) {
        if (ModSettings.INSTANCE.getENABLE_CHEATS().getValue() && Cheats.INSTANCE.getINSTANT_MINING_COOLDOWN().getBooleanValue()) {
            this.field_3716 = 5;
        }
    }

}
