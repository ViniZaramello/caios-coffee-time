package com.example.caios_coffee_time

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import caios_coffee_time.composeapp.generated.resources.Res
import caios_coffee_time.composeapp.generated.resources.caiosCoffeeTimeLogo
import com.example.caios_coffee_time.components.ReloadButton
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource

@OptIn(kotlin.time.ExperimentalTime::class)
fun isCoffeeTime(): Boolean {
    val now = kotlin.time.Clock.System.now()
    val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    return localDateTime.hour < 16
}

@OptIn(kotlin.time.ExperimentalTime::class)
fun getCurrentTime(): String {
    val now = kotlin.time.Clock.System.now()
    val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    val h = localDateTime.hour.toString().padStart(2, '0')
    val m = localDateTime.minute.toString().padStart(2, '0')
    val s = localDateTime.second.toString().padStart(2, '0')
    return "$h:$m:$s"
}

@Composable
fun App() {
    val isCoffee = remember { mutableStateOf(isCoffeeTime()) }
    val currentTime = remember { mutableStateOf(getCurrentTime()) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000)
        currentTime.value = getCurrentTime()
    }

    MaterialTheme {
        CoffeeTimeScreen(
            isCoffeeTime = isCoffee.value,
            currentTime = currentTime.value,
            onRefresh = {
                isCoffee.value = isCoffeeTime()
                currentTime.value = getCurrentTime()
            }
        )
    }
}

@Composable
fun CoffeeTimeScreen(
    isCoffeeTime: Boolean,
    currentTime: String,
    onRefresh: () -> Unit
) {
    val backgroundColor = if (isCoffeeTime) {
        Color(0xFF90EE90)
    } else {
        Color(0xFFFFB6B6)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .padding(20.dp)
                .widthIn(max = 600.dp)
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xF2FFFFFF), // rgba(255, 255, 255, 0.95)
            shadowElevation = 8.dp,
            border = BorderStroke(1.dp, Color(0x33FFFFFF))
        ) {
            Column(
                modifier = Modifier
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Res.drawable.caiosCoffeeTimeLogo),
                    contentDescription = "Caio's Coffee Time Logo",
                    modifier = Modifier
                        .height(250.dp)
                        .padding(bottom = 24.dp)
                )

                Icon(
                    imageVector = if (isCoffeeTime) Icons.Filled.LocalCafe else Icons.Filled.Block,
                    contentDescription = if (isCoffeeTime) "Coffee time" else "Not coffee time",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 20.dp),
                    tint = if (isCoffeeTime) Color(0xFF6D4C41) else Color(0xFFD32F2F)
                )

                Text(
                    text = if (isCoffeeTime) "É hora do café!" else "Não é hora do café!",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center,
                    lineHeight = 52.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Text(
                    text = if (isCoffeeTime)
                        "Aproveite sua pausa para um cafezinho"
                    else
                        "Ainda não chegou a hora da pausa",
                    fontSize = 24.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 30.dp)
                )

                Text(
                    text = "Horário atual: $currentTime",
                    fontSize = 20.sp,
                    color = Color(0xFF555555),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Motivo: ",
                        fontSize = 16.sp
                    )
                    Text(
                        text = if (isCoffeeTime) "Caio permite beber café nesse horário" else "Caio não permite beber café depois das 16:00",
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    // Status Indicator (círculo verde ou vermelho)
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = if (isCoffeeTime) Color(0xFF4CAF50) else Color(0xFFF44336),
                                shape = CircleShape
                            )
                    )
                }
                ReloadButton(onRefresh)
            }
        }
    }
}
