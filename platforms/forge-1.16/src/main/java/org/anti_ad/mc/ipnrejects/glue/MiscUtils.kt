package org.anti_ad.mc.ipnrejects.glue

import org.anti_ad.mc.common.moreinfo.SemVer
import org.anti_ad.mc.common.vanilla.alias.*

import org.anti_ad.mc.common.vanilla.alias.glue.I18n
import org.anti_ad.mc.ipnrejects.ModInfo

object MiscUtils {
    fun createUpdateChatMessage(new: SemVer): Text = LiteralText("")
        .appendSibling(LiteralText("${ModInfo.MOD_NAME}:")
                           .apply {
                               style = Style.EMPTY
                                   .setBold(true)
                                   .setColor(TextColor.fromTextFormatting(Formatting.AQUA))
                           }
        )
        .appendSibling(LiteralText(I18n.translate("ipnrejects.update.version"))
                           .apply {
                               style = Style.EMPTY
                           })
        .appendSibling(LiteralText("'$new'")
                           .apply {
                               style = Style.EMPTY
                                   .setBold(true)
                                   .setColor(TextColor.fromTextFormatting(Formatting.DARK_GREEN))
                           })
        .appendSibling(LiteralText(I18n.translate("ipnrejects.update.available"))
                           .apply {
                               style = Style.EMPTY
                           })
        .appendSibling(LiteralText(I18n.translate("ipnrejects.update.get")))
        .appendSibling(LiteralText("\"Modrinth\"")
                           .apply {
                               style = Style.EMPTY
                                   .setBold(true)
                                   .setColor(TextColor.fromTextFormatting(Formatting.DARK_GREEN))
                                   .setUnderlined(true)
                                   .setClickEvent(ClickEvent(ClickEventAction.OPEN_URL, ModInfo.MODRINTH_URL))
                                   .setHoverEvent(createHoverEventText(ModInfo.MODRINTH_URL))
                           })
        .appendSibling(LiteralText(I18n.translate("ipnrejects.update.or"))
                           .apply { style = Style.EMPTY })
        .appendSibling(LiteralText("\"CurseForge\"")
                           .apply {
                               style = Style.EMPTY
                                   .setBold(true)
                                   .setColor(TextColor.fromTextFormatting(Formatting.DARK_RED))
                                   .setUnderlined(true)
                                   .setClickEvent(ClickEvent(ClickEventAction.OPEN_URL, ModInfo.CURSEFORGE_URL))
                                   .setHoverEvent(createHoverEventText(ModInfo.CURSEFORGE_URL))
                           })
}
