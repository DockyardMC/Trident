package io.github.dockyardmc.trident.common

class HandlerList {
    internal val list = mutableListOf<EventListener<*>>()

    var listeners = arrayOf<EventListener<*>>()
        private set

    fun add(listener: EventListener<*>) {
        list += listener
        bake()
    }

    fun remove(listener: EventListener<*>) {
        list -= listener
        bake()
    }

    fun isEmpty() = list.isEmpty()

    fun bake() {
        listeners = list.toTypedArray()
    }

}