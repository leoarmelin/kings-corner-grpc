package com.leoarmelin.kingscorner.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.kingscorner.ui.theme.Brown500

@Composable
fun BigButton(
    label: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(200.dp)
            .height(70.dp)
            .border(5.dp, Color.Black, RoundedCornerShape(20.dp)),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Brown500,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = label, fontSize = 20.sp)
    }
}