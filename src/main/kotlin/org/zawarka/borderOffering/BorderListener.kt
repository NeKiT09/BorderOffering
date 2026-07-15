package org.zawarka.borderOffering

import io.papermc.paper.event.world.border.WorldBorderBoundsChangeEvent
import io.papermc.paper.event.world.border.WorldBorderBoundsChangeFinishEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ItemSpawnEvent
import org.zawarka.borderOffering.border.WorldBorderExpandingInfo

class BorderListener : Listener {




   @EventHandler
   fun onItemBorder(e: ItemSpawnEvent) {
       e.entity


   }

}