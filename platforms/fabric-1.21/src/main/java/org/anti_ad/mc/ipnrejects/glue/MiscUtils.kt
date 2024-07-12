package org.anti_ad.mc.ipnrejects.glue

import org.anti_ad.mc.alias.text.ClickEvent
import org.anti_ad.mc.alias.text.ClickEventAction
import org.anti_ad.mc.alias.text.MutableText
import org.anti_ad.mc.alias.text.Style
import org.anti_ad.mc.alias.text.Text
import org.anti_ad.mc.alias.text.createHoverEventText
import org.anti_ad.mc.alias.util.Formatting
import org.anti_ad.mc.common.moreinfo.SemVer


import org.anti_ad.mc.common.vanilla.alias.glue.I18n
import org.anti_ad.mc.ipnrejects.ModInfo

object MiscUtils {
    fun createUpdateChatMessage(new: SemVer): MutableText = Text.literal("")
        .append(Text.literal("${ModInfo.MOD_NAME}:")
                    .apply {
                        style = Style.EMPTY
                            .withBold(true)
                            .withColor(Formatting.AQUA)
                    }
        )
        .append(Text.literal(I18n.translate("ipnrejects.update.version"))
                    .apply {
                        style = Style.EMPTY
                    })
        .append(Text.literal("'$new'")
                    .apply {
                        style = Style.EMPTY
                            .withBold(true)
                            .withColor(Formatting.DARK_GREEN)
                    })
        .append(Text.literal(I18n.translate("ipnrejects.update.available"))
                    .apply {
                        style = Style.EMPTY
                    })
        .append(I18n.translate("ipnrejects.update.get"))
        .append(Text.literal("\"Modrinth\"")
                    .apply {
                        style = Style.EMPTY
                            .withBold(true)
                            .withColor(Formatting.DARK_GREEN)
                            .withUnderline(true)
                            .withClickEvent(ClickEvent(ClickEventAction.OPEN_URL, ModInfo.MODRINTH_URL))
                            .withHoverEvent(createHoverEventText(ModInfo.MODRINTH_URL))
                    })
        .append(Text.literal(I18n.translate("ipnrejects.update.or"))
                    .apply { style = Style.EMPTY })
        .append(Text.literal("\"CurseForge\"")
                    .apply {
                        style = Style.EMPTY
                            .withBold(true)
                            .withColor(Formatting.DARK_RED)
                            .withUnderline(true)
                            .withClickEvent(ClickEvent(ClickEventAction.OPEN_URL, ModInfo.CURSEFORGE_URL))
                            .withHoverEvent(createHoverEventText(ModInfo.CURSEFORGE_URL))
                    })
}
