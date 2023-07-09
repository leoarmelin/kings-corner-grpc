package com.leoarmelin.kingscorner.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leoarmelin.kingscorner.Card
import com.leoarmelin.kingscorner.utils.Constants

@Composable
fun CardSlotView(
    modifier: Modifier = Modifier,
    cardList: List<Card>
) {
    val borderColor by animateColorAsState(
        if (cardList.isEmpty()) Color.Gray
        else Color.Transparent
    )

    Box(
        modifier = modifier
            .height(Constants.cardHeight.dp)
            .aspectRatio(Constants.cardRatio)
            .border(2.dp, borderColor, RoundedCornerShape(8.dp))
    ) {
        cardList.firstOrNull()?.let { firstCard ->
            CardView(card = firstCard)
        }

        if (cardList.size > 1) {
            cardList.lastOrNull()?.let { lastCard ->
                CardView(
                    modifier = Modifier.offset(y = 20.dp),
                    card = lastCard
                )
            }
        }
    }
}



@Preview
@Composable
private fun PreviewOne() {
    CardSlotView(
        cardList = listOf()
    )
}