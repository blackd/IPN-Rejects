package org.anti_ad.mc.ipnrejects.cheats.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface IMixinMinecraft {

    @Accessor("rightClickDelay")
    int getRightClickDelayTimer();

    @Accessor("rightClickDelay")
    void setRightClickDelayTimer(int v);
}
