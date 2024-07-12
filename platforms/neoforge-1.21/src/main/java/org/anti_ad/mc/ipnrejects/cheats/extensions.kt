package org.anti_ad.mc.ipnrejects.cheats

import org.anti_ad.mc.alias.client.MinecraftClient
import org.anti_ad.mc.ipnrejects.cheats.mixin.IMixinMinecraft


inline var MinecraftClient.itemUseCooldown: Int
    get() {
        return (this as IMixinMinecraft).rightClickDelayTimer
    }
    set(value) {
        (this as IMixinMinecraft).rightClickDelayTimer = value
    }
