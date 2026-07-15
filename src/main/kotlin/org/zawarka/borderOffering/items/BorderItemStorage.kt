package org.zawarka.borderOffering.items

import org.bukkit.Material

object BorderItemStorage {

    val itemsInBorder = BorderItemList()
    private var itemsRequired : BorderItemList? = null;

    fun clearItemsInBorder() {
        itemsInBorder.clear();
    }

    fun setReqItems(list: BorderItemList) {
        itemsRequired = list
    }

    fun remainCount(material: Material) : Int{
        return reqCount(material) - itemsInBorder[material]
    }

    fun reqCount(material: Material) : Int = itemsRequired.getOrDefault(material)

    fun reqList() : Map<Material, Int> {
        return itemsRequired?.instance() ?: emptyMap()
    }
}