package org.zawarka.borderOffering.items

import org.bukkit.entity.Item
import org.bukkit.scheduler.BukkitRunnable
import org.zawarka.borderOffering.BorderOffering
import org.zawarka.borderOffering.border.isWorldBorderExpanding
import org.zawarka.borderOffering.border.isNearWorldBorder

class BorderItemTask(val item: Item) {

    private val plugin = BorderOffering.instance

    fun run(delay: Long = 0L, period: Long = 1L){
        object : BukkitRunnable() {
            override fun run() {
                if(item.isDead || !item.isTicking){
                    cancel()
                }

                if(task()){
                    cancel()
                }
            }
        }.runTaskTimerAsynchronously(plugin, delay, period)
    }

    private fun task() : Boolean{
        val world = item.world
        if(world.isWorldBorderExpanding()) return false

        if(item.isNearWorldBorder(0.5)){
            //TODO()
            return true
        }

        return false
    }

}