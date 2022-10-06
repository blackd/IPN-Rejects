package org.anti_ad.mc.ipnrejects.events.management

import org.anti_ad.mc.ipnrejects.events.management.TicksDispatcher.ActionType.PRE

object OnetimeDelayedInit {

    private var doTrigger: Boolean = true

    private val actions = mutableListOf<Pair<Int, () -> Unit>>()

    fun init() {
        TicksDispatcher.register(PRE, this::onTickPre)
    }

    fun register(priority: Int, action: () -> Unit) {
        if (doTrigger) {
            actions.add(Pair(priority, action))
        }
    }

    private fun onTickPre() {
        if (doTrigger) {
            TicksDispatcher.removePre(this::onTickPre)
            doTrigger = false
            actions.sortBy {  ( priority, _) ->
                priority
            }
            actions.forEach { (_, action) ->
                action()
            }
            actions.clear()
        }
    }
}
