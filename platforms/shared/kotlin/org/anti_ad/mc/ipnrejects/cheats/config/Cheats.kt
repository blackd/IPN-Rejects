package org.anti_ad.mc.ipnrejects.cheats.config

import org.anti_ad.mc.common.config.builder.CATEGORY
import org.anti_ad.mc.common.config.builder.ConfigDeclaration
import org.anti_ad.mc.common.config.builder.createBuilder
import org.anti_ad.mc.common.config.builder.keyToggleBool
import org.anti_ad.mc.ipnrejects.config.CONFIG_CATEGORY
import org.anti_ad.mc.ipnrejects.config.registerConfigDeclaration

object Cheats: ConfigDeclaration {
    override val builder = createBuilder()
        .CATEGORY("$CONFIG_CATEGORY.cheating")
    val INSTANT_MINING_COOLDOWN                   /**/ by keyToggleBool(false)
    val DISABLE_BLOCK_BREAKING_COOLDOWN           /**/ by keyToggleBool(false)
    val DISABLE_ITEM_USE_COOLDOWN                 /**/ by keyToggleBool(false)
    val DISABLE_SCREEN_SHAKING_ON_DAMAGE          /**/ by keyToggleBool(false)

    fun init() {
        registerConfigDeclaration(this)
    }
}
