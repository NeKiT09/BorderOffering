package org.zawarka.borderOffering.items.progress

import io.papermc.paper.event.world.border.WorldBorderBoundsChangeEvent
import org.bukkit.FluidCollisionMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAnimationEvent
import org.bukkit.event.player.PlayerAnimationType
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.zawarka.borderOffering.border.isNearWorldBorder
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class ItemProgressListener : Listener {

    companion object {
        const val BORDER_DIST = 10.0
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerMove(e: PlayerMoveEvent) {
        val player = e.player

        if(!player.isProgressVisible()) return

        if(!player.isNearWorldBorder(BORDER_DIST)) {
            player.getProgressEntity().despawn()
            return
        }

        player.getProgressEntity().moveToTheEdge()
    }

    private val reachDistance = 5.0

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBorderClick(e: PlayerAnimationEvent) {
        if (e.animationType != PlayerAnimationType.ARM_SWING) return
        val player = e.player

        val lastDrop = dropItemCooldown[player.uniqueId] ?: 0L
        if (System.currentTimeMillis() - lastDrop < 50) {
            dropItemCooldown.remove(player.uniqueId)
            return
        }

        if (!player.isNearWorldBorder(reachDistance)) return

        val rayTrace = player.rayTraceBlocks(reachDistance, FluidCollisionMode.NEVER)

        if (rayTrace == null) {
            if (player.isProgressVisible()) {
                player.getProgressEntity().despawn()
            } else {
                player.getProgressEntity().spawn(player.world)
            }
        }
    }

    private val dropItemCooldown = ConcurrentHashMap<UUID, Long>()

    @EventHandler
    fun onDropItem(event: PlayerDropItemEvent) {
        dropItemCooldown[event.player.uniqueId] = System.currentTimeMillis()
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBorderExpand(e: WorldBorderBoundsChangeEvent){
        ItemProgressStorage.despawnAllProgressEntities()
    }
}