package com.example.caios_coffee_time.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardText(text: String) {
    Surface(
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xCCFFFFFF),
        border = BorderStroke(4.dp, Color(0xFF6200EE))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text)
            }
        }
    }
}