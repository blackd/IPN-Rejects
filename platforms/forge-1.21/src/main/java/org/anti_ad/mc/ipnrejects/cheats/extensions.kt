package org.anti_ad.mc.ipnrejects.cheats

import net.minecraft.client.Minecraft
import org.anti_ad.mc.ipnrejects.cheats.mixin.IMixinMinecraft


inline var Minecraft.itemUseCooldown: Int
    get() {
        return (this as IMixinMinecraft).rightClickDelayTimer
    }
    set(value) {
        (this as IMixinMinecraft).rightClickDelayTimer = value
    }
