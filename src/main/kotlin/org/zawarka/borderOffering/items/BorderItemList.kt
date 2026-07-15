package org.zawarka.borderOffering.items

import org.bukkit.Material

data class BorderItemList(private val map: MutableMap<Material, Int> = mutableMapOf()) {

    operator fun contains(material: Material) : Boolean{
        return map.containsKey(material)
    }

    operator fun get(material: Material) = map[material] ?: 0

    operator fun set(material: Material, amount: Int) {
        map[material] = amount
    }

    fun add(material: Material, amount: Int) {
        this[material] = this[material] + amount
    }

    fun instance() : Map<Material, Int>{
        return map
    }

    fun clear(){
        map.clear()
    }
}


fun BorderItemList?.getOrDefault(material: Material, default: Int = 0) = this?.get(material) ?: default

