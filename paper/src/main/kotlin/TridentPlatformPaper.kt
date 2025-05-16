package io.github.dockyardmc.trident.paper

import io.github.dockyardmc.trident.common.platform.TridentPlatform
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class TridentPlatformPaper : TridentPlatform {

    lateinit var plugin: Plugin

    override fun isEventGlobal(event: Any): Boolean {
        return true
    }

    fun initialize(plugin: Plugin) {
        Bukkit.getPluginManager().registerEvents(TridentPaperListeners(plugin), plugin)
    }

}