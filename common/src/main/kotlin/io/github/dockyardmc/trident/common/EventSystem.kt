package io.github.dockyardmc.trident.common

import io.github.dockyardmc.trident.common.platform.Trident
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
import java.util.*
import kotlin.reflect.KClass

abstract class EventSystem : Disposable {
    val eventMap = mutableMapOf<KClass<*>, HandlerList>()
    var filter = EventFilter.EMPTY

    val children = ObjectOpenHashSet<EventSystem>()

    open var parent: EventSystem? = null
        internal set // assigning to this value will fuck everything, there is no point having it public.

    open var name: String = "eventsystem_${UUID.randomUUID().toString().substring(0..7)}"


    /**
     * Registers a new event listener to this EventSystem
     *
     * @param function The function to be run when this event is called
     * @return The newly registered event listener, can be used when unregistering
     */

    inline fun <reified T : Any> on(noinline function: EventListenerFunction<T>): io.github.dockyardmc.trident.common.EventListener<out Any> {
        val eventType = T::class
        val eventListener = EventListener<T>(T::class, function)

        synchronized(eventMap) {
            val eventList = eventMap.getOrPut(eventType) { HandlerList() }
            eventList.add(eventListener)
        }
        return eventListener
    }

    /**
     * @return A flat list of all EventListeners registered to this EventSystem
     */

    fun flatEventList() = eventMap.flatMap { handlerList -> handlerList.value.list }

    /**
     * Dispatches a new event into this EventSystem, this will call
     * all event listeners registered to this system, and dispatch
     * into all child EventSystems
     *
     * @param event The event being called
     * @return true if the event was dispatched, false if it was
     * filtered
     */
    open fun dispatch(event: Any): Boolean {
        if (!Trident.tridentPlatform.isEventGlobal(event) && !filter.check(event)) return false

        val eventType = event::class

        // we create a copy of the children array before running any handlers to
        //  ensure that if any children are added in the following listeners,
        //  we don't call the newly registered events
        children.clone()
        eventMap[eventType]?.let { handlers ->
            handlers.listeners.forEach { executableEvent ->
                executableEvent.dispatchIfMatches(event)
            }
        }

        children.forEach { child -> child.dispatch(event) }
        return true
    }

    /**
     * Unregisters a listener
     * @param listener The EventListener object (returned from `EventSystem.on { ... }`)
     */

    @Synchronized
    open fun unregister(listener: EventListener<Any>) {
        val events = eventMap[listener.type] ?: return
        events.remove(listener)
        if (events.isEmpty()) eventMap.remove(listener.type)
    }

    /**
     * Unregisters all listeners
     *
     * **This does not remove children, use `clearChildren()` to remove all children**
     * @see clearChildren
     */
    @Synchronized
    open fun unregisterAllListeners() {
        eventMap.clear()
    }

    /**
     * Adds a child to this object
     *
     * @param child The child EventSystem to add
     * @throws IllegalStateException if the child is already registered to another parent
     */
    open fun addChild(child: EventSystem) {
        if (child.parent != null && child.parent != this)
            throw IllegalStateException("$child is already registered to parent ${child.parent}.")

        synchronized(children) {
            children += child
            child.parent = this
        }
    }

    /**
     * Removes a child from this object
     * @param child The child EventSystem to remove
     */
    @Synchronized
    open fun removeChild(child: EventSystem) {
        children -= child
        child.parent = null
    }


    /**
     * Removes all child EventSystems from this object
     */
    @Synchronized
    open fun clearChildren() {
        this.children.forEach { child -> child.parent = null }
        this.children.clear()
    }

    /**
     * Removes this EventSystem from its parent, add attaches
     * it directly to a new parent (by default the master Events object)
     *
     * This means that this EventSystem will no longer inherit filters,
     * direct dispatches, other behaviour from its current parents.
     *
     * @param parent The new parent to attach this object to (defaults to `Events`)
     */
    fun fork(parent: EventSystem = Events) {
        dispose()
        parent.addChild(this)
    }

    /**
     * Detaches this EventSystem from its current parent. This does
     * not remove its children, or unregister any of its listeners.
     * But will no longer receive any events
     */
    fun detachParent() {
        parent?.removeChild(this)
    }

    /**
     * Detaches this EventSystem from its parent and clears all
     * children and event listeners
     */
    override fun dispose() {
        detachParent()
        clearChildren()
        eventMap.clear()
    }

    /**
     * @return A text representation of the tree below
     * this EventSystem, used for debugging
     */
    fun debugTree(indent: Int = 1): String {
        return buildString {
            append(name)
            children.forEach { child ->
                appendLine()
                append(" ".repeat(indent))
                append("⇒ ")
                append(child.debugTree(indent + 1))
            }
        }
    }

    override fun toString(): String {
        return "EventSystem($name)"
    }
}