package org.zawarka.borderOffering.border

import org.bukkit.Location
import org.bukkit.WorldBorder
import kotlin.math.abs

/**
 * Основной метод (Extension-функция).
 * Определяет, нужно ли двигаться параллельно оси X относительно ближайшего бортика.
 */
fun Location.isNearestBorderParallelToX(): Boolean {
    val world = world ?: throw IllegalArgumentException("Location must have a world")
    return WorldBorderEdgeUtils.isParallelToX(world.worldBorder, x, z)
}

object WorldBorderEdgeUtils {
    fun isParallelToX(border: WorldBorder, x: Double, z: Double): Boolean {
        val dx = x - border.centerX()
        val dz = z - border.centerZ()

        return (dx * dx) <= (dz * dz)
    }

    /**
     * Генерирует список точек, расположенных параллельно ближайшему бортику границы мира.
     *
     * @param center Точка, относительно которой генерируются остальные точки (и от которой определяется ближайший бортик).
     * @param count Количество точек для генерации.
     * @param distance Расстояние между соседними точками.
     * @return Список сгенерированных Location.
     */
    fun generateParallelLocations(
        center: Location,
        count: Int,
        distance: Double
    ): List<Location> {
        require(count > 0) { "Количество точек (count) должно быть больше нуля" }

        val world = center.world ?: throw IllegalArgumentException("Location must have a world")

        // 1. Используем ранее написанный метод.
        // Вызываем его 1 раз ДО цикла, чтобы избежать лишних вычислений.
        val isParallelToX = center.isNearestBorderParallelToX()

        // Извлекаем координаты в примитивы, чтобы не дергать геттеры Location в цикле
        val cx = center.x
        val cy = center.y
        val cz = center.z
        val yaw = center.yaw
        val pitch = center.pitch

        // 2. Предварительная аллокация списка нужного размера.
        // Это критически важно для оптимизации, так как ArrayList не будет тратить время
        // на создание новых массивов и копирование данных при добавлении элементов.
        val locations = ArrayList<Location>(count)

        // 3. Рассчитываем базовое смещение для идеального центрирования точек.
        // Например, если count = 3, halfOffset = 1.0. Индексы 0, 1, 2 дадут смещения -1, 0, 1.
        val halfOffset = (count - 1) / 2.0

        for (i in 0 until count) {
            // Вычисляем смещение для текущей точки
            val step = (i - halfOffset) * distance

            // В зависимости от оси, меняем либо X, либо Z
            val finalX = if (isParallelToX) cx + step else cx
            val finalZ = if (isParallelToX) cz else cz + step

            // Создаем Location, передавая примитивы напрямую (самый быстрый конструктор)
            locations.add(Location(world, finalX, cy, finalZ, yaw, pitch))
        }

        return locations
    }

    fun getClosestBorderPoint(border: WorldBorder, loc: Location): Location {
        val h = border.size * 0.5

        val minX = border.centerX() - h; val maxX = border.centerX() + h
        val minZ = border.centerZ() - h; val maxZ = border.centerZ() + h

        return if (isParallelToX(border, loc.x, loc.z))
        // Ближайшая стена параллельна оси X (Z константа)
            Location(loc.world, loc.x.coerceIn(minX, maxX), loc.y, if (loc.z >= border.centerZ()) maxZ else minZ)
        else
        // Ближайшая стена параллельна оси Z (X константа)
            Location(loc.world, if (loc.x >= border.centerX()) maxX else minX, loc.y, loc.z.coerceIn(minZ, maxZ))
    }

    fun getClosestBorderPointWithRotation(border: WorldBorder, loc: Location, offset: BorderOffset = BorderOffset()): Location {
        val h = border.size * 0.5

        val cx = border.centerX()
        val cz = border.centerZ()

        val minX = cx - h; val maxX = cx + h
        val minZ = cz - h; val maxZ = cz + h

        if (isParallelToX(border, loc.x, loc.z)) {
            // Стена параллельна оси X (Северная или Южная)
            val isSouth = loc.z >= cz
            val targetZ = if (isSouth) maxZ else minZ
            val yaw = if (isSouth) 180f else 0f

            // Направление к центру: Юг -> -Z (-1.0), Север -> +Z (1.0)
            val centerDirZ = if (isSouth) -1.0 else 1.0

            val finalX = loc.x.coerceIn(minX, maxX) + offset.alongWall
            val finalY = loc.y + offset.vertical
            val finalZ = targetZ + (offset.towardsCenter * centerDirZ)

            return (Location(border.world, finalX, finalY, finalZ, yaw, 0f))
        } else {
            // Стена параллельна оси Z (Западная или Восточная)
            val isEast = loc.x >= cx
            val targetX = if (isEast) maxX else minX
            val yaw = if (isEast) 90f else 270f

            // Направление к центру: Восток -> -X (-1.0), Запад -> +X (1.0)
            val centerDirX = if (isEast) -1.0 else 1.0

            val finalX = targetX + (offset.towardsCenter * centerDirX)
            val finalY = loc.y + offset.vertical
            val finalZ = loc.z.coerceIn(minZ, maxZ) + offset.alongWall

            return (Location(border.world, finalX, finalY, finalZ, yaw, 0f))
        }
    }
}

