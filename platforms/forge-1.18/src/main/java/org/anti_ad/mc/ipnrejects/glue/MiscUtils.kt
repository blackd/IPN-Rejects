package org.anti_ad.mc.ipnrejects.glue

import org.anti_ad.mc.common.moreinfo.SemVer
import org.anti_ad.mc.common.vanilla.alias.*

import org.anti_ad.mc.common.vanilla.alias.glue.I18n
import org.anti_ad.mc.ipnrejects.ModInfo

object MiscUtils {
    fun createUpdateChatMessage(new: SemVer): Text = LiteralText("")
        .append(LiteralText("${ModInfo.MOD_NAME}:")
                           .apply {
                               style = Style.EMPTY
                                   .withBold(true)
                                   .withColor(Formatting.AQUA)
                           }
        )
        .append(LiteralText(I18n.translate("ipnrejects.update.version"))
                           .apply {
                               style = Style.EMPTY
                           })
        .append(LiteralText("'$new'")
                           .apply {
                               style = Style.EMPTY
                                   .withBold(true)
                                   .withColor(Formatting.DARK_GREEN)
                           })
        .append(LiteralText(I18n.translate("ipnrejects.update.available"))
                           .apply {
                               style = Style.EMPTY
                           })
        .append(LiteralText(I18n.translate("ipnrejects.update.get")))
        .append(LiteralText("\"Modrinth\"")
                           .apply {
                               style = Style.EMPTY
                                   .withBold(true)
                                   .withColor(Formatting.DARK_GREEN)
                                   .withUnderlined(true)
                                   .withClickEvent(ClickEvent(ClickEventAction.OPEN_URL, ModInfo.MODRINTH_URL))
                                   .withHoverEvent(createHoverEventText(ModInfo.MODRINTH_URL))
                           })
        .append(LiteralText(I18n.translate("ipnrejects.update.or"))
                           .apply { style = Style.EMPTY })
        .append(LiteralText("\"CurseForge\"")
                           .apply {
                               style = Style.EMPTY
                                   .withBold(true)
                                   .withColor(Formatting.DARK_RED)
                                   .withUnderlined(true)
                                   .withClickEvent(ClickEvent(ClickEventAction.OPEN_URL,ModInfo.CURSEFORGE_URL))
                                   .withHoverEvent(createHoverEventText(ModInfo.CURSEFORGE_URL))
                           })
}
