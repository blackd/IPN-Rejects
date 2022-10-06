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

package org.anti_ad.mc.ipnrejects.forge

import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.IExtensionPoint
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.client.ConfigGuiHandler
import org.anti_ad.mc.common.vanilla.alias.Screen
import org.anti_ad.mc.ipnrejects.gui.screens.ConfigScreen
import org.anti_ad.mc.ipnrejects.init


class IPNRejectsClientInit: Runnable {

    override fun run() {

        MinecraftForge.EVENT_BUS.register(ForgeTicksSource())

        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest::class.java) {
            IExtensionPoint.DisplayTest({ ModLoadingContext.get().activeContainer.modInfo.version.toString() }) {
                    remote: String?, isServer: Boolean? -> true
            }
        }

        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory::class.java) {
            ConfigGuiHandler.ConfigGuiFactory { minecraft: Minecraft?, screen: Screen? -> ConfigScreen() }
        }

        init()
    }
}
