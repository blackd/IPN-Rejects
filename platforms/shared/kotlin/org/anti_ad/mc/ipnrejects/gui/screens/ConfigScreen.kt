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

package org.anti_ad.mc.ipnrejects.gui.screens

import org.anti_ad.mc.alias.text.TextSerializer
import org.anti_ad.mc.alias.text.fromSerializedJson
import org.anti_ad.mc.alias.text.getTranslatable
import org.anti_ad.mc.common.config.CategorizedMultiConfig
import org.anti_ad.mc.common.config.builder.ConfigDeclaration
import org.anti_ad.mc.common.config.builder.addTo
import org.anti_ad.mc.common.config.builder.toMultiConfigList
import org.anti_ad.mc.common.config.options.BaseConfigKeyToggleBooleanInputHandler
import org.anti_ad.mc.common.config.options.ConfigKeyToggleBoolean
import org.anti_ad.mc.common.gui.screen.ConfigScreenBase
import org.anti_ad.mc.common.config.builder.keyToggleBool as libIPNKeyToggleBool
import org.anti_ad.mc.common.gui.widgets.toListWidget
import org.anti_ad.mc.common.input.KeybindSettings
import org.anti_ad.mc.common.input.KeybindSettings.Companion.INGAME_DEFAULT
import org.anti_ad.mc.common.moreinfo.InfoManager
import org.anti_ad.mc.common.vanilla.Vanilla
import org.anti_ad.mc.common.vanilla.*
import org.anti_ad.mc.common.vanilla.alias.*
import org.anti_ad.mc.common.vanilla.alias.glue.I18n
import org.anti_ad.mc.ipnrejects.Log
import org.anti_ad.mc.ipnrejects.ModInfo
import org.anti_ad.mc.ipnrejects.RejectsInfoManager
import org.anti_ad.mc.ipnrejects.config.Configs
import org.anti_ad.mc.ipnrejects.config.Debugs
import org.anti_ad.mc.ipnrejects.config.ModSettings
import org.anti_ad.mc.ipnrejects.config.SaveLoadManager

private const val BUTTON_PREFIX = "ipnrejects.gui.config."
private const val DISPLAY_NAME_PREFIX = "ipnrejects.config.name."
private const val DESCRIPTION_PREFIX = "ipnrejects.config.description."

object ConfigScreeHelper: BaseConfigKeyToggleBooleanInputHandler() {

    @JvmStatic
    fun toggleBooleanSettingMessage(value: Boolean,
                                    key: String) {
        val message: () -> String = {
            val newLine = """{"text": " : ", "color": "#FFFFFF"},"""
            val colour = if (value) {
                "#1f9716"
            } else {
                "#c60926"
            }
            val yesNo = if (value) {
                "ipnrejects.gui.config.on"
            } else {
                "ipnrejects.gui.config.off"
            }
            val name = DISPLAY_NAME_PREFIX + key

            """[
             {"translate" : "$name", "color" : "#20fdf6" },
             $newLine
             {"translate" : "$yesNo", "color": "$colour"}
             ]"""
        }

        fromSerializedJson(message())?.let {
            Vanilla.inGameHud().setOverlayMessage(it,
                                                  true)
        }
    }

    fun finish() {
        SaveLoadManager.save()
    }

    fun ConfigDeclaration.keyToggleBool(defaultValue: Boolean,
                                        defaultSettings: KeybindSettings = INGAME_DEFAULT) =
        ConfigKeyToggleBoolean(defaultValue,
                               ConfigScreeHelper::finish,
                               ConfigScreeHelper::toggleBooleanSettingMessage,
                               defaultSettings = defaultSettings)
            .also {
                allToggleSettings.add(it)
            }.addTo(this)
}


class ConfigScreen(private val gui: Boolean = false) : ConfigScreenBase(getTranslatable("ipnrejects.gui.config.title",
                                                                                        ModInfo.MOD_VERSION)) {


    companion object {
        var storedSelectedIndex = 0
    }

    private fun getDisplayNameId(key: String): String {
        val id = DISPLAY_NAME_PREFIX + key
        Log.trace("getDisplayNameId -> $id")
        return id
    }

    private fun getDescriptionNameId(key: String): String {
        val id = DESCRIPTION_PREFIX + key
        Log.trace("getDescriptionNameId -> $id", Exception())
        return id
    }

    private fun CategorizedMultiConfig.toListWidget() =
        this.toListWidget(
            { I18n.translateOrElse(getDisplayNameId(it)) { it } },
            { I18n.translateOrEmpty(getDescriptionNameId(it)) },
            { I18n.translateOrElse(it) { it.substringAfterLast('.') } }
        )

    init {
        openConfigMenuHotkey = ModSettings.OPEN_CONFIG_MENU
        // hide debugs class
        val toRemove = mutableSetOf<ConfigDeclaration>()
        if (!ModSettings.DEBUG.booleanValue) toRemove.add(Debugs)
        val configsToUse = Configs - toRemove
        configsToUse.toMultiConfigList().forEach { multi ->
            addNavigationButtonWithWidget(I18n.translate(BUTTON_PREFIX + multi.key)) { multi.toListWidget() }
        }
        selectedIndex = storedSelectedIndex
    }

    override fun closeScreen() {
        //RejectsInfoManager.event(if (gui) "gui/" else {""} + "closeConfig")
        storedSelectedIndex = selectedIndex
        SaveLoadManager.save()
        super.closeScreen()
    }
}
