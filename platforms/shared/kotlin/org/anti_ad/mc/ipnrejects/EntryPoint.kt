package org.anti_ad.mc.ipnrejects

import org.anti_ad.mc.ipnrejects.cheats.CheatsInit
import org.anti_ad.mc.ipnrejects.config.initMainConfig
import org.anti_ad.mc.ipnrejects.events.management.OnetimeDelayedInit

var initGlueProc: (() -> Unit) = ::initGlues;

private fun initGlues() {

    initGlueProc = ::nop

}

private fun nop() {}

fun init() {
    initGlueProc()

    initMainConfig()
    CheatsInit()
    specificInit()

    OnetimeDelayedInit.registerPost(0, RejectsInfoManager::doCheckVersion)
    OnetimeDelayedInit.init()

}
