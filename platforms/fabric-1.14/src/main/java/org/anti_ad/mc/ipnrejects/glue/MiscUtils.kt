package org.anti_ad.mc.ipnrejects.glue

import org.anti_ad.mc.common.moreinfo.SemVer

import org.anti_ad.mc.common.vanilla.alias.Formatting
import org.anti_ad.mc.common.vanilla.alias.LiteralText
import org.anti_ad.mc.common.vanilla.alias.createHoverEventText
import org.anti_ad.mc.common.vanilla.alias.glue.I18n
import org.anti_ad.mc.common.vanilla.alias.ClickEvent
import org.anti_ad.mc.common.vanilla.alias.ClickEventAction
import org.anti_ad.mc.common.vanilla.alias.Style
import org.anti_ad.mc.common.vanilla.alias.Text
import org.anti_ad.mc.ipnrejects.ModInfo

object MiscUtils {
    fun createUpdateChatMessage(new: SemVer): Text = LiteralText("")
        .append(LiteralText("${ModInfo.MOD_NAME}:")
                    .apply {
                        style = Style()
                            .setBold(true)
                            .setColor(Formatting.AQUA)
                    }
        )
        .append(LiteralText(I18n.translate("ipnrejects.update.version"))
                    .apply {
                        style = Style()
                    })
        .append(LiteralText("'$new'")
                    .apply {
                        style = Style()
                            .setBold(true)
                            .setColor(Formatting.DARK_GREEN)
                    })
        .append(LiteralText(I18n.translate("ipnrejects.update.available"))
                    .apply {
                        style = Style()
                    })
        .append(I18n.translate("ipnrejects.update.get"))
        .append(LiteralText("\"Modrinth\"")
                    .apply {
                        style = Style()
                            .setBold(true)
                            .setColor(Formatting.DARK_GREEN)
                            .setUnderline(true)
                            .setClickEvent(ClickEvent(ClickEventAction.OPEN_URL, ModInfo.MODRINTH_URL))
                            .setHoverEvent(createHoverEventText(ModInfo.MODRINTH_URL))
                    })
        .append(LiteralText(I18n.translate("ipnrejects.update.or"))
                    .apply { style = Style() })
        .append(LiteralText("\"CurseForge\"")
                    .apply {
                        style = Style()
                            .setBold(true)
                            .setColor(Formatting.DARK_RED)
                            .setUnderline(true)
                            .setClickEvent(ClickEvent(ClickEventAction.OPEN_URL, ModInfo.CURSEFORGE_URL))
                            .setHoverEvent(createHoverEventText(ModInfo.CURSEFORGE_URL))
                    })
}
