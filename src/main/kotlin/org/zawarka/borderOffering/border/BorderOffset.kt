package org.zawarka.borderOffering.border

import org.bukkit.Location
import org.bukkit.WorldBorder

data class BorderOffset(
    val alongWall: Double = 0.0,
    val vertical: Double = 0.0,
    val towardsCenter: Double = 0.0
)
{
    companion object {
        fun fromLocations(base: Location, target: Location, border: WorldBorder = base.world.worldBorder): BorderOffset {
            val cx = border.center.x
            val cz = border.center.z

            // Вертикальное смещение всегда одинаково, независимо от стены
            val vertical = target.y - base.y

            return if (WorldBorderEdgeUtils.isParallelToX(border, base.x, base.z)) {
                // Стена параллельна оси X (Северная или Южная)
                val isSouth = base.z >= cz
                val centerDirZ = if (isSouth) -1.0 else 1.0 // Направление к центру по Z

                // Смещение к центру: инвертируем дельту Z, если мы на южной стене
                val towardsCenter = (target.z - base.z) * centerDirZ
                // Смещение вдоль стены: это просто изменение по X
                val alongWall = target.x - base.x

                BorderOffset(alongWall = alongWall, vertical = vertical, towardsCenter = towardsCenter)
            } else {
                // Стена параллельна оси Z (Западная или Восточная)
                val isEast = base.x >= cx
                val centerDirX = if (isEast) -1.0 else 1.0 // Направление к центру по X

                // Смещение к центру: инвертируем дельту X, если мы на восточной стене
                val towardsCenter = (target.x - base.x) * centerDirX
                // Смещение вдоль стены: это просто изменение по Z
                val alongWall = target.z - base.z

                BorderOffset(alongWall = alongWall, vertical = vertical, towardsCenter = towardsCenter)
            }
        }
    }
}