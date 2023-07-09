package com.leoarmelin.kingscorner.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.dp
import com.leoarmelin.kingscorner.Board.Field
import com.leoarmelin.kingscorner.utils.Constants
import com.leoarmelin.kingscorner.utils.getAlignmentByIndex
import com.leoarmelin.kingscorner.utils.getRotationByIndex

@Composable
fun BoardView(
    modifier: Modifier = Modifier,
    fieldList: List<Field>,
    onPositioned: (Field, Offset) -> Unit
) {
    val height = rememberBoardHeight()
    val width = rememberBoardWidth()

    Box(
        modifier = modifier
            .width(width)
            .height(height)
    ) {
        fieldList.indices.forEach { index ->
            val alignment = remember { getAlignmentByIndex(index) }
            val rotation = remember { getRotationByIndex(index) }
            var hasBeenPositioned = remember { false }

            fieldList.getOrNull(index)?.let { field ->
                CardSlotView(
                    modifier = Modifier
                        .onGloballyPositioned { lc ->
                            // Ignore the first load
                            if (hasBeenPositioned) return@onGloballyPositioned
                            hasBeenPositioned = true
                            onPositioned(
                                field,
                                lc.positionInWindow() + Offset(Constants.cardWidth / 2, 0f)
                            )
                        }
                        .align(alignment)
                        .rotate(rotation),
                    cardList = field.cardsList
                )
            }
        }
    }
}

@Composable
private fun rememberBoardHeight() = remember {
    (Constants.cardHeight * 3 + 24).dp
}

@Composable
private fun rememberBoardWidth() = remember {
    (Constants.cardHeight * Constants.cardRatio * 3 + 48).dp
}