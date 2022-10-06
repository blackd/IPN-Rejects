package org.anti_ad.mc.ipnrejects.cheats

import org.anti_ad.mc.common.vanilla.Vanilla
import org.anti_ad.mc.ipnrejects.cheats.config.Cheats
import org.anti_ad.mc.ipnrejects.config.ModSettings
import org.anti_ad.mc.ipnrejects.events.management.TicksDispatcher

object ItemUseCooldown {

    fun init() {
        TicksDispatcher.register(TicksDispatcher.ActionType.PRE, this::onTick)
    }

    private fun onTick() {
        if (Vanilla.mc().itemUseCooldown > 0 && ModSettings.ENABLE_CHEATS.value && Cheats.DISABLE_ITEM_USE_COOLDOWN.value) {
            Vanilla.mc().itemUseCooldown = 0;
        }
    }

}
