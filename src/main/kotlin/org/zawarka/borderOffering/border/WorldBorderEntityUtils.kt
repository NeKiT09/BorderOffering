package org.zawarka.borderOffering.border

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.TextDisplay
import org.zawarka.borderOffering.border.WorldBorderEdgeUtils.isParallelToX
import kotlin.math.abs
import kotlin.math.min

fun Entity.isNearWorldBorder(distance: Double = 4.0): Boolean {
    val border = world.worldBorder
    val halfSize = border.size * 0.5
    val cx = border.center.x
    val cz = border.center.z

    // Математическое расстояние до отрезка [c - h, c + h] без ветвлений
    val distX = abs(abs(location.x - cx) - halfSize)
    val distZ = abs(abs(location.z - cz) - halfSize)

    return min(distX, distZ) <= distance
}

fun Entity.snapAndAlignToBorder(offset: BorderOffset = BorderOffset()) {
    teleport(WorldBorderEdgeUtils.getClosestBorderPointWithRotation(world.worldBorder, location, offset))
}