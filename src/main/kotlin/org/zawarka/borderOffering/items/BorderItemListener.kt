package org.zawarka.borderOffering.items

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ItemSpawnEvent
import org.zawarka.borderOffering.border.isNearWorldBorder

class BorderItemListener : Listener{

    companion object{
        const val BORDER_DISTANCE: Double = 10.0
    }

    @EventHandler
    fun onItemDrop(e: ItemSpawnEvent) {
        val item = e.entity

        if (!item.isNearWorldBorder(BORDER_DISTANCE)) return

        BorderItemTask(item).run()
    }
}