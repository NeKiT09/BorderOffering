package org.zawarka.borderOffering.border

import org.bukkit.entity.Entity
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