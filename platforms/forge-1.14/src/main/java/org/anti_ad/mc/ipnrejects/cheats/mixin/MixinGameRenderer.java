package org.anti_ad.mc.ipnrejects.cheats.mixin;

import net.minecraft.client.renderer.GameRenderer;
import org.anti_ad.mc.ipnrejects.cheats.config.Cheats;
import org.anti_ad.mc.ipnrejects.config.ModSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MixinGameRenderer
 */
@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Inject(at = @At("HEAD"), method = "hurtCameraEffect(F)V", cancellable = true)
    public void bobViewWhenHurt(float float_1, CallbackInfo info) {
        if (ModSettings.INSTANCE.getENABLE_CHEATS().getValue() && Cheats.INSTANCE.getDISABLE_SCREEN_SHAKING_ON_DAMAGE().getBooleanValue()) {
            info.cancel();
        }
    }
}
