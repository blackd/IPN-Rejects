package org.anti_ad.mc.ipnrejects.events.management


object TicksDispatcher {

    enum class ActionType {
        PRE,
        POST,
    }

    private val pre = mutableListOf<()->Unit>()
    private val post = mutableListOf<()->Unit>()

    fun register(type: ActionType, action: ()->Unit) {
        when (type) {
            ActionType.PRE -> {
                pre.add(action)
            }
            ActionType.POST -> {
                post.add(action)
            }
        }
    }

    fun dispatchPre() {
        val l = pre.toList()
        l.forEach{
            it()
        }
    }

    fun dispatchPost() {
        val l = post.toList()
        l.forEach{
            it()
        }
    }

    fun removePre(action: () -> Unit) {
        pre.remove(action)
    }

}
