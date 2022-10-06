/*
 * Inventory Profiles Next
 *
 *   Copyright (c) 2019-2020 jsnimda <7615255+jsnimda@users.noreply.github.com>
 *   Copyright (c) 2021-2022 Plamen K. Kosseff <p.kosseff@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

@file:Suppress("unused")

package org.anti_ad.mc.ipnrejects.config

import org.anti_ad.mc.common.Savable
import org.anti_ad.mc.common.config.builder.CATEGORY
import org.anti_ad.mc.common.config.builder.ConfigDeclaration
import org.anti_ad.mc.common.config.builder.ConfigSaveLoadManager
import org.anti_ad.mc.common.config.builder.bool
import org.anti_ad.mc.common.config.builder.createBuilder
import org.anti_ad.mc.common.config.builder.hotkey
import org.anti_ad.mc.common.config.builder.keyToggleBool
import org.anti_ad.mc.common.config.builder.toMultiConfig
import org.anti_ad.mc.common.input.KeybindSettings
import org.anti_ad.mc.common.vanilla.glue.VanillaUtil
import kotlin.math.sign


const val CONFIG_CATEGORY = "ipnrejects.config.category"

object ModSettings : ConfigDeclaration {

    override val builder = createBuilder()

        .CATEGORY("§§vgap:3")
    val ENABLE_CHEATS                        /**/ by keyToggleBool(false)
        .CATEGORY("$CONFIG_CATEGORY.general_hotkeys")
    val OPEN_CONFIG_MENU                     /**/ by hotkey("M,L",
                                                            KeybindSettings.INGAME_DEFAULT)
        .CATEGORY("$CONFIG_CATEGORY.privacy")
    val ENABLE_UPDATES_CHECK                 /**/ by bool(true)
    val ENABLE_ANALYTICS                     /**/ by bool(false)

        .CATEGORY("$CONFIG_CATEGORY.debug")
    val DEBUG                                /**/ by bool(false)

        .CATEGORY("§§hide - first run")
    val FIRST_RUN                            /**/ by bool(true)
}

object Debugs: ConfigDeclaration {
    override val builder = createBuilder()

        .CATEGORY("§§vgap:3")
    val TRACE_LOGS                                /**/ by bool(false)
}


object Hotkeys : ConfigDeclaration {
    override val builder = createBuilder()

        .CATEGORY("$CONFIG_CATEGORY.hotkeys")
    val OPEN_CONFIG_MENU                          /**/ by hotkey("M,L",
                                                                 KeybindSettings.INGAME_DEFAULT)
}

fun initMainConfig() {
    registerConfigDeclaration(ModSettings,
                              Debugs)
}


const val FILE_PATH = "ipn-rejects/main-config.json"

fun registerConfigDeclaration(vararg cd: ConfigDeclaration) {
    when (cd.size) {
        0 -> {
            return
        }
        1 -> {
            Configs.add(cd[0])
        }
        else -> {
            cd.forEach {
                Configs.add(it)
            }
        }
    }
}

val Configs = mutableListOf<ConfigDeclaration>()

object SaveLoadManager: Savable {

    private val configFolder = VanillaUtil.configDirectory("ipn-rejects")

    private val manager: Lazy<ConfigSaveLoadManager> = lazy(LazyThreadSafetyMode.NONE) {
        ConfigSaveLoadManager(Configs.toMultiConfig(), FILE_PATH)
    }

    override fun load() {
        manager.value.load()
    }

    override fun save() {
        manager.value.save()
    }

}
