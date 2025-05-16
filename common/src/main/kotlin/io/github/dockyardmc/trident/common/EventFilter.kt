package io.github.dockyardmc.trident.common

fun interface EventFilter {

    companion object {
        val EMPTY = EventFilter { true }
    }

    fun check(event: Any): Boolean
}