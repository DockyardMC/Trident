package io.github.dockyardmc.trident.paper

import io.github.dockyardmc.trident.common.Events
import io.github.dockyardmc.trident.common.platform.Trident
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class TestPlugin : JavaPlugin() {

    val platform = TridentPlatformPaper()

    override fun onEnable() {
        Trident.setPlatform(platform)
        platform.initialize(this)

        Events.on<PlayerDropItemEvent> { event ->
            Bukkit.broadcastMessage("§d${event.player} has dropped ${event.itemDrop.name}")
        }
    }
}