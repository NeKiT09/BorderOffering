package org.zawarka.borderOffering.border

import org.bukkit.World
import org.bukkit.WorldBorder

object WorldBorderCenterCash {

    private val centerX = mutableMapOf<World, Double>()
    private val centerZ = mutableMapOf<World, Double>()

    fun set(world: World, x: Double = world.worldBorder.center.x, z: Double = world.worldBorder.center.z) {
        centerX[world] = x
        centerZ[world] = z
    }

    fun getX(world: World) : Double {
       return centerX.getOrElse(world){
            set(world)
           return getX(world)
        }
    }

    fun getZ(world: World) : Double{
        return centerZ.getOrElse(world){
            set(world)
            return getZ(world)
        }
    }
}

fun World.centerX() = WorldBorderCenterCash.getX(this)
fun World.centerZ() = WorldBorderCenterCash.getZ(this)

fun WorldBorder.centerX() = world!!.centerX()
fun WorldBorder.centerZ() = world!!.centerZ()