package org.anti_ad.mc.ipnrejects.cheats

import org.anti_ad.mc.ipnrejects.cheats.config.Cheats

object CheatsInit {

    operator fun invoke() {
        Cheats
        ItemUseCooldown.init()
    }
}
