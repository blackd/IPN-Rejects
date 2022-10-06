package org.anti_ad.mc.ipnrejects.cheats.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
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
    @Inject(at = @At("HEAD"), method = "hurtCameraEffect(Lcom/mojang/blaze3d/matrix/MatrixStack;F)V", cancellable = true)
    public void bobViewWhenHurt(MatrixStack matrixStackIn, float partialTicks, CallbackInfo ci) {
        if (ModSettings.INSTANCE.getENABLE_CHEATS().getValue() && Cheats.INSTANCE.getDISABLE_SCREEN_SHAKING_ON_DAMAGE().getBooleanValue()) {
            ci.cancel();
        }
    }
}
