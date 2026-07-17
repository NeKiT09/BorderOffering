package org.zawarka.borderOffering.border

import org.bukkit.World

object WorldBorderExpandingInfo {

    private val isBorderExpanding = mutableMapOf<World, Boolean>()

    fun setExpanding(world: World, isExpanding: Boolean) {
        isBorderExpanding[world] = isExpanding
    }

    fun isExpanding(world: World) = isBorderExpanding[world] ?: false
}

fun World.isWorldBorderExpanding() = WorldBorderExpandingInfo.isExpanding(this)
