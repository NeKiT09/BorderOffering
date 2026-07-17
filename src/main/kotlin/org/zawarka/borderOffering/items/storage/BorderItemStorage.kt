package org.zawarka.borderOffering.items.storage

import org.bukkit.Material

object BorderItemStorage {

    private val itemsInBorder = BorderItemList()
    private var itemsRequired : BorderItemList = BorderItemList.empty();

    fun clearItemsInBorder() {
        itemsInBorder.clear();
    }

    fun addItemInBorder(material: Material, amount: Int){
        itemsInBorder[material] = itemsInBorder[material] + amount
    }

    fun setReqItems(list: BorderItemList) {
        itemsRequired = list
    }

    fun remainCount(material: Material) : Int{
        return reqCount(material) - itemsInBorder[material]
    }

    fun reqCount(material: Material) : Int = itemsRequired[material]

    fun reqList() : Map<Material, Int> {
        return itemsRequired.instance()
    }

    fun reqMaterials() : Set<Material>{
        return reqList().keys
    }
}