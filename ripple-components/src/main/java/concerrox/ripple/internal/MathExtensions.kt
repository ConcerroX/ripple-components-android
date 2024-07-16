package concerrox.ripple.internal

import kotlin.math.PI

fun toRadians(deg: Float): Float = (deg / 180.0 * PI).toFloat()

fun toDegrees(rad: Float): Float = (rad * 180.0 / PI).toFloat()