package com.leoarmelin.kingscorner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.leoarmelin.kingscorner.components.BoardView
import com.leoarmelin.kingscorner.components.HandView
import com.leoarmelin.kingscorner.models.FieldPosition
import com.leoarmelin.kingscorner.viewmodels.MainViewModel

@Composable
fun GameScreen(
    mainViewModel: MainViewModel
) {
    val fieldsList by mainViewModel.fieldsList.collectAsState()
    val hand by mainViewModel.hand.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        Column {
            Button(onClick = { mainViewModel.createGame() }) {
                Text("Create")
            }

            Button(onClick = { mainViewModel.beginGame() }) {
                Text("Begin")
            }

            Button(onClick = { mainViewModel.pass() }) {
                Text("Pass")
            }
        }

        BoardView(
            modifier = Modifier.align(Alignment.Center),
            fieldList = fieldsList,
            onPositioned = { field, offset ->
                mainViewModel.addFieldPosition(
                    FieldPosition(field.fieldNumber, offset)
                )
            }
        )

        HandView(
            modifier = Modifier.align(Alignment.BottomCenter),
            cardList = hand,
            onDragEnd = { card, offset ->
                mainViewModel.dropCardOnBoard(card, offset)
            }
        )
    }
}