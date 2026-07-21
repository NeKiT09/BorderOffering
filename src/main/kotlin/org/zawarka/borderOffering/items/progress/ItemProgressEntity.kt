package org.zawarka.borderOffering.items.progress

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Item
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.TextDisplay
import org.bukkit.inventory.ItemStack
import org.zawarka.borderOffering.BorderOffering
import org.zawarka.borderOffering.border.BorderOffset
import org.zawarka.borderOffering.utils.setScale
import org.zawarka.borderOffering.utils.setTeleportDuration
import org.zawarka.borderOffering.utils.setTranslation
import java.util.UUID

class ItemProgressEntity(val material: Material, val amount: Int, val requiredAmount: Int) {

    fun spawn(loc: Location, uuid: UUID) : Pair<UUID, UUID> {
        val world = loc.world

        val stack = ItemStack(material)
//        val item = world.dropItem(loc, stack)
//
//        item.setGravity(false)
//        item.isUnlimitedLifetime = true
//        item.setCanMobPickup(false)
//        item.setCanPlayerPickup(false)
//        item.pickupDelay = 9999999
//        item.isInvulnerable = true
//        item.setWillAge(false)

        val item = world.spawnEntity(loc, EntityType.ITEM_DISPLAY) as ItemDisplay

        item.itemStack = stack
        item.setScale(0.45f)
        item.setTranslation(0f, 0.55f ,0f)
        item.setTeleportDuration(1)
        item.isPersistent = true
        item.isVisibleByDefault = false

        val text = world.spawnEntity(loc, EntityType.TEXT_DISPLAY) as TextDisplay

        text.text(Component.text("$amount/$requiredAmount"))
        text.setTeleportDuration(1)
        text.isPersistent = true
        text.isVisibleByDefault = false

        val player = Bukkit.getPlayer(uuid)

        if(player != null){
            player.showEntity(BorderOffering.instance, item)
            player.showEntity(BorderOffering.instance, text)
        }

        return item.uniqueId to text.uniqueId
    }


}