package org.zawarka.borderOffering.border

import org.bukkit.Location
import org.bukkit.WorldBorder

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
        val center = border.center

        val dx = x - center.x
        val dz = z - center.z

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
}

