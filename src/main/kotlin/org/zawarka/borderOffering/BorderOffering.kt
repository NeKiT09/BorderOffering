 package org.zawarka.borderOffering

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.zawarka.borderOffering.border.WorldBorderMovementListener
import org.zawarka.borderOffering.items.BorderItemListener
import org.zawarka.borderOffering.items.progress.ItemProgressListener
import org.zawarka.borderOffering.items.storage.BorderItemList
import org.zawarka.borderOffering.items.storage.BorderItemListProvider
import org.zawarka.borderOffering.items.storage.BorderItemStorage

 class BorderOffering : JavaPlugin() {

     companion object{
         lateinit var instance: BorderOffering private set
     }

     override fun onEnable() {
         instance = this


         BorderItemStorage[Bukkit.getWorld("world")!!] =
             BorderItemStorage(itemsRequired = BorderItemListProvider.getRandomList())

         regListeners()
     }

     private fun regListeners() {
         registerEvents(WorldBorderMovementListener())
         registerEvents(BorderItemListener())
         registerEvents(ItemProgressListener())
     }

     private fun registerEvents(listener: Listener){
         Bukkit.getPluginManager().registerEvents(listener, this)
     }

     override fun onDisable() {
         // Plugin shutdown logic
     }
 }
