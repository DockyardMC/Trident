package io.github.dockyardmc.trident.common.platform

object Trident {
    lateinit var tridentPlatform: TridentPlatform

    fun setPlatform(platform: TridentPlatform) {
        this.tridentPlatform = platform
    }
}