package org.anti_ad.mc.ipnrejects.cheats.config

import org.anti_ad.mc.common.config.builder.*
import org.anti_ad.mc.ipnrejects.config.CONFIG_CATEGORY
import org.anti_ad.mc.ipnrejects.config.RejectsConfigScreenSettings

object Cheats: ConfigDeclaration {
    override val builder = createBuilder()
        .CATEGORY("$CONFIG_CATEGORY.cheating")
    val INSTANT_MINING_COOLDOWN                   /**/ by keyToggleBool(RejectsConfigScreenSettings, false)
    val DISABLE_BLOCK_BREAKING_COOLDOWN           /**/ by keyToggleBool(RejectsConfigScreenSettings, false)
    val DISABLE_ITEM_USE_COOLDOWN                 /**/ by keyToggleBool(RejectsConfigScreenSettings, false)
    val DISABLE_SCREEN_SHAKING_ON_DAMAGE          /**/ by keyToggleBool(RejectsConfigScreenSettings, false)

    init {
        RejectsConfigScreenSettings.registerConfigDeclaration(this)
    }
}
