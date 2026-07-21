package org.zawarka.borderOffering.items.storage

import org.bukkit.Material
import org.bukkit.World

fun World.getItemStorage() = BorderItemStorage[this]

class BorderItemStorage(
    val itemsInBorder : BorderItemList = BorderItemList.empty(),
    val itemsRequired : BorderItemList = BorderItemList.empty()
) {
    companion object {
        private val storageMap = mutableMapOf<World, BorderItemStorage>()

        operator fun get(world: World): BorderItemStorage = storageMap[world] ?: createEmpty(world)

        fun createEmpty(world: World): BorderItemStorage {
            BorderItemStorage().apply {
                storageMap[world] = this
                return this
            }
        }

        operator fun set(world: World, storage: BorderItemStorage) {
            storageMap[world] = storage
        }
    }

    fun clearItemsInBorder() {
        itemsInBorder.clear();
    }

    fun addItemInBorder(material: Material, amount: Int){
        itemsInBorder[material] = itemsInBorder[material] + amount
    }

    fun setReqItems(list: BorderItemList) {
        itemsRequired.clone(list)
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