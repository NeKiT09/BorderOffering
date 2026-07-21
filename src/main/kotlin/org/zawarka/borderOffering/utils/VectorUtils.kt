package org.zawarka.borderOffering.utils

import org.bukkit.Location
import org.bukkit.util.Vector


object VectorUtils {
    fun getVectorBetween(start: Location, end: Location) =
        Vector(end.x - start.x, end.y - start.y, end.z - start.z)
}