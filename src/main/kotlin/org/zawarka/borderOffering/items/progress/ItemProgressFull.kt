package org.zawarka.borderOffering.items.progress

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector
import org.zawarka.borderOffering.border.BorderOffset
import org.zawarka.borderOffering.border.WorldBorderEdgeUtils
import org.zawarka.borderOffering.border.snapAndAlignToBorder
import org.zawarka.borderOffering.items.storage.BorderItemList
import org.zawarka.borderOffering.items.storage.getItemStorage
import org.zawarka.borderOffering.utils.VectorUtils
import java.util.UUID

class ItemProgressFull(val uuid: UUID) {
    companion object{
        const val DISTANCE_BETWEEN : Double = 1.0
    }

    val spawnedEntities: MutableMap<Pair<UUID, UUID>, BorderOffset> = mutableMapOf()

    fun spawn(world: World){
        val loc = edgeLocation(world) ?: return

        val storage = loc.world.getItemStorage();

        val requiredItems = storage.itemsRequired
        val itemsAvailable = storage.itemsInBorder

        val entities = ArrayList<ItemProgressEntity>(requiredItems.materials().size).apply {
            for (entry in requiredItems.instance()) {
                this.add(ItemProgressEntity(entry.key, itemsAvailable[entry.key], entry.value))
            }
        }

        val entityCount = entities.size

        for ((i, location) in WorldBorderEdgeUtils.generateParallelLocations(loc, entityCount, DISTANCE_BETWEEN)
            .withIndex()) {
            spawnedEntities[entities[i].spawn(location, uuid)] = BorderOffset.fromLocations(loc, location)
        }

        ItemProgressVisibility.setVisible(uuid)
    }

    fun despawn(){
        for (pair in spawnedEntities.keys) {
            removeEntity(pair.first)
            removeEntity(pair.second)
        }
        spawnedEntities.clear()

        ItemProgressVisibility.removeVisible(uuid)
    }

    fun moveToTheEdge(){
        for ((pair, offset) in spawnedEntities) {
            moveToTheEdge(pair.first, offset)
            moveToTheEdge(pair.second, offset)
        }
    }

    private fun moveToTheEdge(uuid: UUID, offset: BorderOffset){
        val entity = Bukkit.getEntity(uuid) ?: return

        val edge = edgeLocation(entity.world, offset) ?: return

        entity.teleport(edge.add(0.0, 1.0, 0.0))
    }

    private fun edgeLocation(world: World, offset: BorderOffset = BorderOffset()) : Location?{
        val player = Bukkit.getPlayer(uuid) ?: return null
        if(player.world != world) return null

        return WorldBorderEdgeUtils.getClosestBorderPointWithRotation(world.worldBorder, player.location, offset)
    }

    private fun removeEntity(uuid: UUID){
        val entity = Bukkit.getEntity(uuid)

        entity?.remove()
    }

}