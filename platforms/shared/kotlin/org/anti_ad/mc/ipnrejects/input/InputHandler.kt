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

package org.anti_ad.mc.ipnrejects.input

import org.anti_ad.mc.common.IInputHandler
import org.anti_ad.mc.common.config.options.ConfigKeyToggleBoolean
import org.anti_ad.mc.common.extensions.tryCatch
import org.anti_ad.mc.common.input.GlobalInputHandler
import org.anti_ad.mc.common.vanilla.VanillaScreenUtil
import org.anti_ad.mc.ipnrejects.config.Hotkeys
import org.anti_ad.mc.ipnrejects.gui.screens.ConfigScreeHelper
import org.anti_ad.mc.ipnrejects.gui.screens.ConfigScreen


object InputHandler : IInputHandler {

    object CancellableInputHandler : IInputHandler {
        override fun onInput(lastKey: Int,
                             lastAction: Int): Boolean {
            return false
        }
    }

    override fun onInput(lastKey: Int,
                         lastAction: Int): Boolean {
        return tryCatch(false) {
            if (Hotkeys.OPEN_CONFIG_MENU.isActivated()) {
                VanillaScreenUtil.openScreen(ConfigScreen().also { it.dumpWidgetTree() })
            }


            ConfigScreeHelper.checkAll()

            return false
        }
    }

    fun onClientInit() {
        GlobalInputHandler.register(this)
        GlobalInputHandler.registerCancellable(CancellableInputHandler)
    }
}
