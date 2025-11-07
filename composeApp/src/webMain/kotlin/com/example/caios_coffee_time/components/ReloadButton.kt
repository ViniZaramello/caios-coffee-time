package com.example.caios_coffee_time.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReloadButton(onRefresh: () -> Unit) {
    Button(
        onClick = onRefresh,
        modifier = Modifier.padding(top = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6200EE)
        ),
        shape = RoundedCornerShape(25.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 0.dp
        )
    ) {
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = "Refresh",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "ATUALIZAR STATUS",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}