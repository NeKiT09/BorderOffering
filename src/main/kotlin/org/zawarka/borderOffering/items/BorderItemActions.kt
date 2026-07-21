package org.zawarka.borderOffering.items

import org.bukkit.Bukkit
import org.bukkit.entity.Item
import org.zawarka.borderOffering.BorderOffering
import org.zawarka.borderOffering.border.WorldBorderExpansionUtils
import org.zawarka.borderOffering.items.storage.getItemStorage
import org.zawarka.borderOffering.utils.removeAmount
import kotlin.math.min

object BorderItemActions {

    fun onItemTouchBorder(item: Item) {
        val storage = item.world.getItemStorage();

        val material = item.itemStack.type

        val count = storage.remainCount(material)

        if (count <= 0) return

        val availableCount = min(item.itemStack.amount, count)

        storage.addItemInBorder(material, availableCount)

        item.removeAmount(availableCount)

        WorldBorderExpansionUtils.expandBorder(item.world, -0.0001, 0)
        WorldBorderExpansionUtils.expandBorder(item.world, 0.0001, 1)
    }
}