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

import org.anti_ad.mc.alias.SharedConstants
import org.anti_ad.mc.common.events.OnetimeDelayedInit
import org.anti_ad.mc.ipnrejects.cheats.ItemUseCooldown
import org.anti_ad.mc.ipnrejects.config.ModSettings
import org.anti_ad.mc.ipnrejects.config.RejectsConfigScreenSettings


private fun initInfoManager() {
    RejectsInfoManager.loader = "forge"
    RejectsInfoManager.mcVersion = SharedConstants.getCurrentVersion().id

}


fun specificInit() {


    OnetimeDelayedInit.register(Int.MIN_VALUE) {
        ModInfo.MOD_VERSION = ModInfo.getModVersion()
        initInfoManager()


        ItemUseCooldown.init()

        RejectsConfigScreenSettings.saveLoadManager.load()
        //CustomDataFileLoader.load()
        if (ModSettings.FIRST_RUN.booleanValue) {
            ModSettings.FIRST_RUN. value = false
            RejectsConfigScreenSettings.saveLoadManager.save()
        }
        RejectsInfoManager.event(lazy {"${RejectsInfoManager.loader}/${RejectsInfoManager.mcVersion}/${RejectsInfoManager.version}/started"})
    }
}
