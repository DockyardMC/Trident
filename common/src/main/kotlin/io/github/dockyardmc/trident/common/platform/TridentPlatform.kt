package io.github.dockyardmc.trident.common.platform

interface TridentPlatform {

    fun isEventGlobal(event: Any): Boolean

}