package org.zawarka.borderOffering.items.storage

import org.bukkit.Material

object BorderItemListProvider {


    fun getRandomList() : BorderItemList {
        val list = BorderItemList()

        list.add(Material.CARVED_PUMPKIN, 16)
        list.add(Material.DIAMOND, 32)

        return list
    }

}