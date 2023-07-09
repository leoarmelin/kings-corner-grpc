package com.leoarmelin.kingscorner.components

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.kingscorner.Card
import com.leoarmelin.kingscorner.extensions.appRank
import com.leoarmelin.kingscorner.extensions.appSuit
import com.leoarmelin.kingscorner.extensions.getChar
import com.leoarmelin.kingscorner.extensions.getColor
import com.leoarmelin.kingscorner.extensions.getIcon
import com.leoarmelin.kingscorner.models.CardRank
import com.leoarmelin.kingscorner.models.CardSuit
import com.leoarmelin.kingscorner.utils.Constants
import kotlin.math.roundToInt

// Here we will care about the card states: whether it's being dragged
// or flipped, these kind of states.
@Composable
fun CardView(
    modifier: Modifier = Modifier,
    card: Card,
    hasShadow: Boolean = false,
    onDragEnd: ((Card, Offset) -> Unit)? = null
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val offset by animateIntOffsetAsState(
        targetValue = IntOffset(offsetX.roundToInt(), offsetY.roundToInt()),
        animationSpec = spring()
    )
    var rootOffset = remember { Offset.Zero }

    CardViewContent(
        modifier = modifier
            .offset { offset }
            .then(if (hasShadow) Modifier.shadow(3.dp) else Modifier)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        if (onDragEnd == null) return@detectDragGestures
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    },
                    onDragStart = { offset ->
                        offsetX += offset.x - Constants.cardWidth
                        offsetY += offset.y - Constants.cardHeight
                    },
                    onDragEnd = {
                        if (onDragEnd == null) return@detectDragGestures
                        onDragEnd(
                            card,
                            rootOffset + Offset(offsetX, offsetY) + Constants.cardMiddleOffset
                        )
                        offsetX = 0f
                        offsetY = 0f
                    }
                )
            }
            .onGloballyPositioned { lc ->
                rootOffset = lc.positionInWindow()
            },
        suit = card.appSuit,
        rank = card.appRank
    )
}

// The wrapper is necessary because previews can't handle generated
// models like `Card`. Here we will only care about the card UI.
@Composable
private fun CardViewContent(
    modifier: Modifier = Modifier,
    suit: CardSuit,
    rank: CardRank,
) {
    val height = remember { Constants.cardHeight }
    val color = rememberSuitColor(suit)
    val rankTextSize = rememberTextSize(height, 0.11f)
    val suitTextSize = rememberTextSize(height, 0.08f)
    val bigSuitTextSize = rememberTextSize(height, 0.16f)
    val suitIcon = rememberSuitIcon(suit)
    val rankChar = rememberRankChar(rank)
    val cardPadding = rememberPadding(height)

    Box(
        modifier = modifier
            .height(height.dp)
            .aspectRatio(Constants.cardRatio)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(cardPadding)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = rankChar, fontSize = rankTextSize, color = color)
            Text(text = suitIcon, fontSize = suitTextSize, color = color)
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = suitIcon,
            fontSize = bigSuitTextSize,
            color = color
        )

        Column(
            modifier = Modifier.align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = suitIcon, fontSize = suitTextSize, color = color)
            Text(text = rankChar, fontSize = rankTextSize, color = color)
        }
    }
}

@Composable
private fun rememberSuitColor(suit: CardSuit) = remember(suit) {
    suit.getColor()
}

@Composable
private fun rememberSuitIcon(suit: CardSuit) = remember(suit) {
    suit.getIcon()
}

@Composable
private fun rememberRankChar(rank: CardRank) = remember(rank) {
    rank.getChar()
}

@Composable
private fun rememberTextSize(height: Int, ratio: Float) = remember(height) {
    (height * ratio).sp
}

@Composable
private fun rememberPadding(height: Int) = remember(height) {
    (height * 0.05f).dp
}

@Preview
@Composable
private fun PreviewOne() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CardViewContent(
            suit = CardSuit.Heart,
            rank = CardRank.Five
        )
    }
}
