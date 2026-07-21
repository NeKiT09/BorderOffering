package org.zawarka.borderOffering.items.progress

import org.bukkit.entity.Player
import java.util.UUID

fun Player.isProgressVisible() = ItemProgressVisibility.isProgressVisible(this.uniqueId)

object ItemProgressVisibility {
    
    private val visibilityMap = mutableSetOf<UUID>()
    
    fun isProgressVisible(uuid: UUID): Boolean {
        return visibilityMap.contains(uuid)
    }
    
    fun setVisible(uuid: UUID) {
        visibilityMap.add(uuid)
    }
    
    fun removeVisible(uuid: UUID) {
        visibilityMap.remove(uuid)
    }
}