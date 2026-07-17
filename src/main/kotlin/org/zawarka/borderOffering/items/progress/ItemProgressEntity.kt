package org.zawarka.borderOffering.items.progress

import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Item
import org.bukkit.entity.TextDisplay
import org.bukkit.inventory.ItemStack
import java.util.UUID

class ItemProgressEntity(val material: Material, val amount: Int, val requiredAmount: Int) {

    fun spawn(loc: Location) : Pair<UUID, UUID> {
        val world = loc.world

        val stack = ItemStack(material)
        val item = world.dropItem(loc, stack)

        item.setGravity(false)
        item.isUnlimitedLifetime = true
        item.setCanMobPickup(false)
        item.setCanPlayerPickup(false)
        item.pickupDelay = 9999999
        item.isInvulnerable = true
        item.setWillAge(false)

        val text = world.spawnEntity(loc, EntityType.TEXT_DISPLAY) as TextDisplay

        text.text(Component.text("$amount/$requiredAmount"))

        return item.uniqueId to text.uniqueId
    }


}