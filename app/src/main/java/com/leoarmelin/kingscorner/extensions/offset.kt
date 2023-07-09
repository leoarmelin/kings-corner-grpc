package com.leoarmelin.kingscorner.extensions

import androidx.compose.ui.geometry.Offset
import com.leoarmelin.kingscorner.utils.Constants
import kotlin.math.cos
import kotlin.math.sin

// Here in case of rotated rectangle not behaviour well
//fun Offset.isInsideOfEllipse(offset: Offset): Boolean {
//    val xPart = (this.x - offset.x).toDouble().pow(2) / Constants.cardWidth.pow(2)
//    val yPart = (this.y - offset.y).toDouble().pow(2) / Constants.cardHeight.toDouble().pow(2)
//
//    return xPart + yPart <= 1
//}

fun Offset.isInsideOfRotatedRectangle(offset: Offset, angle: Float): Boolean {
    // rotate around rectangle center by -rectAngle
    val s = sin(-angle)
    val c = cos(-angle)
    val rectCenter = offset + Constants.cardMiddleOffset

    // set origin to rect center
    var newPoint = this - rectCenter
    // rotate
    newPoint = Offset(newPoint.x * c - newPoint.y * s, newPoint.x * s + newPoint.y * c)
    // put origin back
    newPoint += rectCenter

    // check if our transformed point is in the rectangle, which is no longer
    // rotated relative to the point
    return newPoint.x >= (offset.x - Constants.cardMiddleOffset.x) &&
            newPoint.x <= (offset.x + Constants.cardWidth + Constants.cardMiddleOffset.x) &&
            newPoint.y >= offset.y - Constants.cardMiddleOffset.y &&
            newPoint.y <= offset.y + Constants.cardHeight + Constants.cardMiddleOffset.y
}