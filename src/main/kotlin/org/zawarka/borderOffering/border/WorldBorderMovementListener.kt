package org.zawarka.borderOffering.border

import io.papermc.paper.event.world.border.WorldBorderBoundsChangeEvent
import io.papermc.paper.event.world.border.WorldBorderBoundsChangeFinishEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class WorldBorderMovementListener : Listener {

    @EventHandler
    fun onBorderExpand(event: WorldBorderBoundsChangeEvent){
        WorldBorderExpandingInfo.setExpanding(event.world, true)
    }

    @EventHandler
    fun onBorderExpandEnd(event: WorldBorderBoundsChangeFinishEvent){
        WorldBorderExpandingInfo.setExpanding(event.world, false)
    }

}