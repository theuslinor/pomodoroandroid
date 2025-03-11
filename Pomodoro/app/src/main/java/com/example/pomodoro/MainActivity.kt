package com.example.pomodoro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            PomodoroScreen()
        }
    }
}

@Composable
fun PomodoroScreen(){
    var timeLeft by remember { mutableStateOf("25:00")}

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = timeLeft, fontSize = 48.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Button(onClick = { /* Iniciar Timer */ }) {
                Text("Iniciar")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { /* Pausar Timer */}) {
                Text("Pausar")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { /* Reiniciar Timer */}) {
                Text("Reiniciar")
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PomodoroTheme {
        Greeting("Android")
    }
}*/