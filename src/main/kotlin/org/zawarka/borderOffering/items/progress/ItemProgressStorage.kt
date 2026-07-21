package org.zawarka.borderOffering.items.progress

import org.bukkit.entity.Player
import java.util.UUID


fun Player.getProgressEntity() : ItemProgressFull{
    return ItemProgressStorage.getProgress(this)
}

object ItemProgressStorage {

    private val progressMap = mutableMapOf<UUID, ItemProgressFull>()

    fun getProgress(player: Player): ItemProgressFull {
        return progressMap[player.uniqueId] ?: createProgress(player)
    }

    fun createProgress(player: Player) : ItemProgressFull {
        ItemProgressFull(player.uniqueId).let { progress ->
            progressMap[player.uniqueId] = progress
            return progress
        }
    }

    fun despawnAllProgressEntities(){
        for (progress in progressMap.values) {
            progress.despawn()
        }
    }
}