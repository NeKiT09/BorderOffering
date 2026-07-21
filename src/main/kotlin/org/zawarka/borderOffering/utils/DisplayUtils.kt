package org.zawarka.borderOffering.utils

import org.bukkit.entity.Display


fun Display.setTeleportDuration(duration: Int){
    DisplayEntityUtils.setTeleportDuration(this, duration)
}

fun Display.setScale(scale: Float) {
    val transformation = transformation
    transformation.scale.set(scale)
    this.transformation = transformation
}

fun Display.setTranslation(x: Float, y: Float = x, z: Float = x) {
    val transformation = transformation
    transformation.translation.set(x, y, z)
    this.transformation = transformation
}

object DisplayEntityUtils {

    private val setTeleportDurationMethod = try {
        Display::class.java.getMethod("setTeleportDuration", Int::class.javaPrimitiveType)
    } catch (_: NoSuchMethodException) {
        null
    }

    fun setTeleportDuration(display: Display, duration: Int) {
        setTeleportDurationMethod?.invoke(display, duration)
    }
}