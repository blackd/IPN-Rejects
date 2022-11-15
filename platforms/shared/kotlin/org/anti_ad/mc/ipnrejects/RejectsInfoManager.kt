/*
 * Inventory Profiles Next
 *
 *   Copyright (c) 2022 Plamen K. Kosseff <p.kosseff@gmail.com>
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

package org.anti_ad.mc.ipnrejects

import org.anti_ad.mc.common.TellPlayer
import org.anti_ad.mc.common.gui.widgets.ButtonWidget
import org.anti_ad.mc.common.gui.widgets.ConfigButtonInfo
import org.anti_ad.mc.common.moreinfo.InfoManagerBase

import org.anti_ad.mc.common.vanilla.Vanilla
import org.anti_ad.mc.common.vanilla.alias.glue.I18n

import org.anti_ad.mc.ipnrejects.config.ModSettings
import org.anti_ad.mc.ipnrejects.glue.MiscUtils.createUpdateChatMessage

import java.net.URL
import kotlin.concurrent.timer

object RejectsInfoManager: InfoManagerBase() {
    override var mcVersion = "game-version-missing"
    override var version: String
        get() = ModInfo.MOD_VERSION
        set(value) {}
    override var modId = ModInfo.MOD_ID
    override var modName = ModInfo.MOD_NAME
    override var loader: String = "loader-missing"

    override val defaultRequest: Map<String, String> = mapOf("domain" to "ipn-stats.anti-ad.org",
                                                             "name" to "pageview")

    override val target: URL = URL("https://p.anti-ad.org/api/event")

    private val versionCheckUrl = URL("https://ipn.anti-ad.org/ipn/rejectsVersionCheckV3")

    override var isEnabled: () -> Boolean  = { ModSettings.ENABLE_ANALYTICS.value }


    fun doCheckVersion() {
        timer("versionCheck", initialDelay = 5000, period = 10000) {
            val player = Vanilla.playerNullable()
            if (player != null && version != "null") {
                val salt = player.gameProfile.id?.toString() ?: " InvalidName"
                this.cancel()
                checkVersion(versionCheckUrl, "Rejects", salt) { new, current, isBeta ->
                    if (ModSettings.ENABLE_UPDATES_CHECK.value) {
                        timer(name = "versionMessage", initialDelay = 5000, period = 10000) {
                            Vanilla.queueForMainThread {
                                val clickableMsg = createUpdateChatMessage(new)
                                TellPlayer.chat(clickableMsg)
                            }
                            this.cancel()
                        }
                    }
                }
            }
        }
    }

    object DoVersionCheckButtonInfo : ConfigButtonInfo() {
        override val buttonText: String
            get() = I18n.translate("ipnrejects.gui.config.button.do_version_check")

        override fun onClick(widget: ButtonWidget) {
            doCheckVersion()
        }
    }
}
