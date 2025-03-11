package com.example.pomodoro

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            PomodoroScreen(
                onStart = {playSound(R.raw.startpomodoro)},
                onPause = {playSound(R.raw.pausepomodoro)},
                onFinish = {playSound(R.raw.finishpomodoro)}
            )
        }
    }

    private fun playSound(soundResId: Int){
        val mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer.start()
    }
}

@Composable
fun PomodoroScreen(
    onStart: () -> Unit,
    onPause: () -> Unit,
    onFinish: () -> Unit
){
    var timeLeft by remember { mutableStateOf(25 * 60 * 1000L)} // 25 minutos em milissegundos.
    var isRunning by remember { mutableStateOf(false) }
    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    fun startTimer(){
        if (!isRunning) {
            isRunning = true
            onStart()
            timer = object : CountDownTimer(timeLeft, 1000){
                override fun onTick(millisUntilFinished: Long) {
                    timeLeft = millisUntilFinished
                }

                override fun onFinish() {
                    isRunning = false
                    onFinish()
                }
            }.start()
        }
    }

    fun pauseTimer(){
        timer?.cancel()
        isRunning = false
        onPause()
    }

    fun resetTimer(){
        timer?.cancel()
        timeLeft = 25 * 60 * 1000L
        isRunning = false
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = formatTime(timeLeft), fontSize = 48.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Button(onClick = { startTimer() }) { Text("Iniciar") }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { pauseTimer() }) { Text("Pausar") }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { resetTimer() }) { Text("Reiniciar") }
        }
    }
}

fun formatTime(millis: Long): String{
    val minutes = (millis / 1000) / 60
    val seconds = (millis / 1000) % 60
    return String.format("%02d:%02d", minutes, seconds)
}
