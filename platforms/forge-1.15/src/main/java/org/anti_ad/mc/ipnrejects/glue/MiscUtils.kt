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
        .appendSibling(LiteralText("${ModInfo.MOD_NAME}:")
                           .apply {
                               style = Style()
                                   .setBold(true)
                                   .setColor(Formatting.AQUA)
                           }
        )
        .appendSibling(LiteralText(I18n.translate("ipnrejects.update.version"))
                           .apply {
                               style = Style()
                           })
        .appendSibling(LiteralText("'$new'")
                           .apply {
                               style = Style()
                                   .setBold(true)
                                   .setColor(Formatting.DARK_GREEN)
                           })
        .appendSibling(LiteralText(I18n.translate("ipnrejects.update.available"))
                           .apply {
                               style = Style()
                           })
        .appendSibling(LiteralText(I18n.translate("ipnrejects.update.get")))
        .appendSibling(LiteralText("\"Modrinth\"")
                           .apply {
                               style = Style()
                                   .setBold(true)
                                   .setColor(Formatting.DARK_GREEN)
                                   .setUnderlined(true)
                                   .setClickEvent(ClickEvent(ClickEventAction.OPEN_URL, ModInfo.MODRINTH_URL))
                                   .setHoverEvent(createHoverEventText(ModInfo.MODRINTH_URL))
                           })
        .appendSibling(LiteralText(I18n.translate("ipnrejects.update.or"))
                           .apply { style = Style() })
        .appendSibling(LiteralText("\"CurseForge\"")
                           .apply {
                               style = Style()
                                   .setBold(true)
                                   .setColor(Formatting.DARK_RED)
                                   .setUnderlined(true)
                                   .setClickEvent(ClickEvent(ClickEventAction.OPEN_URL, ModInfo.CURSEFORGE_URL))
                                   .setHoverEvent(createHoverEventText(ModInfo.CURSEFORGE_URL))
                           })
}
