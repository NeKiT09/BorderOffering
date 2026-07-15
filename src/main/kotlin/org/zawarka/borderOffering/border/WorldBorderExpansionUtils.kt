package org.zawarka.borderOffering.border

import org.bukkit.World

object WorldBorderExpansionUtils {

    fun expandBorder(world: World, offset: Double, seconds: Long = 0) {
        val border = world.worldBorder

        border.setSize(border.size + offset, seconds)
    }
}

fun World.expandBorder(offset: Double, seconds: Long = 0){
    WorldBorderExpansionUtils.expandBorder(this, offset, seconds)
}