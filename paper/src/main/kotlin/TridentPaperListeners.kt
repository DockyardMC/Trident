package io.github.dockyardmc.trident.paper

import io.github.dockyardmc.trident.common.Events
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.RegisteredListener

class TridentPaperListeners(val plugin: Plugin) : Listener {

    //TODO(generate massive list of all events. There is no way to listen on all events in paper... god I hate paper)

    init {
        val registeredListener = RegisteredListener(this, ::handleEvent, EventPriority.NORMAL, plugin, false)
        HandlerList.getHandlerLists().forEach { handler ->
            handler.register(registeredListener)
        }
    }

    fun handleEvent(listener: Listener, event: Event) {
        println(event.eventName)
        Events.dispatch(event)
    }

//    @EventHandler
//    fun onEvent(event: PlayerEvent) {
//        Events.dispatch(event)
//    }

}