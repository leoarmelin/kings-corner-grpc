package com.leoarmelin.kingscorner.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.leoarmelin.kingscorner.Card
import com.leoarmelin.kingscorner.utils.Constants

@Composable
fun HandView(
    modifier: Modifier = Modifier,
    cardList: List<Card>,
    onDragEnd: (Card, Offset) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(-((Constants.cardHeight * Constants.cardRatio / 2).dp))
    ) {
        cardList.forEach { card ->
            CardView(
                modifier = Modifier,
                card = card,
                hasShadow = true,
                onDragEnd = onDragEnd
            )
        }
    }
}
