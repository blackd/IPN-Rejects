package org.anti_ad.mc.ipnrejects

import org.anti_ad.mc.common.config.options.ConfigKeyToggleBoolean
import org.anti_ad.mc.ipnrejects.cheats.CheatsInit
import org.anti_ad.mc.ipnrejects.config.Debugs
import org.anti_ad.mc.ipnrejects.config.ModSettings
import org.anti_ad.mc.ipnrejects.config.initMainConfig
import org.anti_ad.mc.ipnrejects.events.management.OnetimeDelayedInit
import org.anti_ad.mc.ipnrejects.gui.screens.ConfigScreeHelper

var initGlueProc: (() -> Unit) = ::initGlues;

private fun initGlues() {

    initGlueProc = ::nop

}

private fun nop() {}

fun init() {
    initGlueProc()

    Log.shouldDebug = { ModSettings.DEBUG.booleanValue }
    Log.shouldTrace = { ModSettings.DEBUG.booleanValue && Debugs.TRACE_LOGS.booleanValue }

    initMainConfig()
    CheatsInit()
    specificInit()

    OnetimeDelayedInit.registerPost(0, RejectsInfoManager::doCheckVersion)
    OnetimeDelayedInit.init()

}
