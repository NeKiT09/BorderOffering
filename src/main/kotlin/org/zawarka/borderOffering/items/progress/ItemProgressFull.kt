package org.zawarka.borderOffering.items.progress

import org.bukkit.Location
import org.zawarka.borderOffering.border.WorldBorderEdgeUtils
import org.zawarka.borderOffering.items.storage.BorderItemList

class ItemProgressFull(val itemsAvailable: BorderItemList, val requiredItems: BorderItemList) {

    companion object{
        const val DISTANCE_BETWEEN : Double = 1.0
    }

    fun spawn(loc: Location){
        val entities = ArrayList<ItemProgressEntity>(requiredItems.materials().size).apply {
            for (entry in requiredItems.instance()) {
                this.add(ItemProgressEntity(entry.key, itemsAvailable[entry.key], entry.value))
            }
        }

        val entityCount = entities.size

        for ((i, location) in WorldBorderEdgeUtils.generateParallelLocations(loc, entityCount, DISTANCE_BETWEEN)
            .withIndex()) {
            entities[i].spawn(location)
        }
    }


}