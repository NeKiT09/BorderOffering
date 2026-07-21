package org.zawarka.borderOffering.border

import io.papermc.paper.event.world.border.WorldBorderBoundsChangeEvent
import io.papermc.paper.event.world.border.WorldBorderBoundsChangeFinishEvent
import io.papermc.paper.event.world.border.WorldBorderCenterChangeEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class WorldBorderMovementListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBorderExpand(event: WorldBorderBoundsChangeEvent){
        WorldBorderExpandingInfo.setExpanding(event.world, true)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBorderExpandEnd(event: WorldBorderBoundsChangeFinishEvent){
        WorldBorderExpandingInfo.setExpanding(event.world, false)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onCenterChange(event: WorldBorderCenterChangeEvent){
        WorldBorderCenterCash.set(event.world, event.newCenter.x, event.newCenter.z)
    }

}