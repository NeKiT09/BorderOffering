package org.zawarka.borderOffering.items

import org.bukkit.entity.Item
import org.bukkit.scheduler.BukkitRunnable
import org.zawarka.borderOffering.BorderOffering
import org.zawarka.borderOffering.border.isWorldBorderExpanding
import org.zawarka.borderOffering.border.isNearWorldBorder
import org.zawarka.borderOffering.items.storage.getItemStorage
import org.zawarka.borderOffering.utils.removeAmount
import kotlin.math.min

class BorderItemTask(val item: Item) {

    private val plugin = BorderOffering.instance

    fun run(delay: Long = 1L, period: Long = 1L){
        object : BukkitRunnable() {
            var ticks: Int = 0;

            override fun run() {
                ticks++
                if(ticks > 15 * 20 || item.isDead || !item.isTicking){
                    cancel()
                    return
                }

                if(task()){
                    cancel()
                }
            }
        }.runTaskTimer(plugin, delay, period)
    }

    private fun task() : Boolean{
        val world = item.world
        if(world.isWorldBorderExpanding()) return false

        if(item.isNearWorldBorder(0.3)){
            BorderItemActions.onItemTouchBorder(item)
            return true
        }

        return false
    }

}