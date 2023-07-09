package com.leoarmelin.kingscorner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BigButton(
    label: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(150.dp)
            .height(50.dp)
            .background(Color.Gray, RoundedCornerShape(20.dp))
            .border(5.dp, Color.Black, RoundedCornerShape(20.dp)),
        onClick = onClick
    ) {
        Text(text = label)
    }
}