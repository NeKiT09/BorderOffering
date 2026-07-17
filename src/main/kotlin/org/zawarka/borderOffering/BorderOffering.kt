 package org.zawarka.borderOffering

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.zawarka.borderOffering.border.WorldBorderMovementListener
import org.zawarka.borderOffering.items.BorderItemListener

 class BorderOffering : JavaPlugin() {

     companion object{
         lateinit var instance: BorderOffering private set
     }

     override fun onEnable() {
         instance = this

         regListeners()
     }

     private fun regListeners() {
         registerEvents(WorldBorderMovementListener())
         registerEvents(BorderItemListener())
     }

     private fun registerEvents(listener: Listener){
         Bukkit.getPluginManager().registerEvents(listener, this)
     }

     override fun onDisable() {
         // Plugin shutdown logic
     }
 }
