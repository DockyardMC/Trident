package io.github.dockyardmc.trident.common

import kotlin.reflect.KClass

typealias EventListenerFunction<T> = (event: T) -> Unit

class EventListener<T : Any>(
    val type: KClass<out Any>,
    val function: EventListenerFunction<T>,
) {
    @Suppress("UNCHECKED_CAST")
    fun dispatchIfMatches(event: Any?) {
        println("${type.isInstance(event)}")
        if (event != null && type.isInstance(event)) {
            function.invoke(event as T)
        }
    }
}