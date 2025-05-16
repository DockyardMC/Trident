package io.github.dockyardmc.trident.common

object Events : EventSystem() {
    override var name: String = "global_events" // it would be really funny if API user renames this

    override fun dispatch(event: Any): Boolean {
        val success = super.dispatch(event)
        return success
    }

    /**
     * Removes all event listeners, and all children from main
     * events tree
     */
    override fun dispose() {
        this.children.clear()
        this.eventMap.clear()
    }

    override fun toString(): String {
        return name
    }
}