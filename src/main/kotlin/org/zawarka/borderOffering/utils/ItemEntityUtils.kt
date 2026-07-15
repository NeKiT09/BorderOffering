package org.zawarka.borderOffering.utils

import org.bukkit.Material
import org.bukkit.entity.Item

fun Item.setAmount(amount: Int) {
    val stack = itemStack
    stack.amount = amount
    itemStack = stack
}

fun Item.removeAmount(amount: Int){
    setAmount(itemStack.amount - amount)
}

fun Item.material() : Material {
    return itemStack.type
}