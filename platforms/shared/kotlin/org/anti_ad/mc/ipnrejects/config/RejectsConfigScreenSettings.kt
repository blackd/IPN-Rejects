package org.anti_ad.mc.ipnrejects.config

import org.anti_ad.mc.alias.text.Text
import org.anti_ad.mc.alias.text.getTranslatable
import org.anti_ad.mc.common.Savable
import org.anti_ad.mc.common.config.builder.ConfigDeclaration
import org.anti_ad.mc.common.config.builder.ConfigSaveLoadManager
import org.anti_ad.mc.common.config.builder.toMultiConfig
import org.anti_ad.mc.common.gui.screen.BaseConfigScreenSettings
import org.anti_ad.mc.ipnrejects.ModInfo

object RejectsConfigScreenSettings: BaseConfigScreenSettings() {

    private const val CONFIG_SCREEN_LABELS_PREFIX = "ipnrejects.gui.config."
    private const val CONFIG_SCREEN_OPTIONS_PREFIX = "ipnrejects.config."

    const val FILE_NAME = "main-config.json"

    val configs = mutableListOf<ConfigDeclaration>()

    private class DebugLastComparable<T>(val self: T): Comparable<DebugLastComparable<T>> {
        override fun compareTo(other: DebugLastComparable<T>): Int {
            if (self === other.self) return 0
            if (self === Debugs) return 1
            if (other.self === Debugs) return -1
            return 0
        }
    }

    fun registerConfigDeclaration(vararg cd: ConfigDeclaration) {
        when (cd.size) {
            0 -> {
                return
            }
            1 -> {
                configs.add(cd[0])
            }
            else -> {
                cd.forEach {
                    configs.add(it)
                }
            }
        }
        configs.sortBy {
            DebugLastComparable(it)
        }
    }

    val saveLoadManager: ConfigSaveLoadManager = ConfigSaveLoadManager(ModInfo.MOD_ID, FILE_NAME) { configs.toMultiConfig() }

    override val configScreenTitle: Text
        get() {
            return getTranslatable("${CONFIG_SCREEN_LABELS_PREFIX}title", ModInfo.MOD_VERSION)
        }


    override val saveManager: Savable = saveLoadManager

    override val configLabelsPrefix = CONFIG_SCREEN_LABELS_PREFIX

    override val configOptionsPrefix = CONFIG_SCREEN_OPTIONS_PREFIX

    override val openConfigHotkey = ModSettings.OPEN_CONFIG_MENU

    override val configDeclarations: List<ConfigDeclaration>
        get() {
            val debug = ModSettings.DEBUG.value
            return if (debug) {
                configs
            } else {
                configs.filter {
                    it !== Debugs
                }
            }
        }

    init {
        registerConfigDeclaration(ModSettings,
                                  Debugs)
    }
}
