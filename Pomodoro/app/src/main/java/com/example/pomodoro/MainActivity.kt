package com.example.pomodoro

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
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
            PomodoroScreen(
                onStart = {playSound(R.raw.startpomodoro)},
                onPause = {playSound(R.raw.pausepomodoro)},
                onFinish = {playSound(R.raw.finishpomodoro)}
            )
        }
    }

    private fun playSound(soundResId: Int){
        val mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer.setVolume(100.0f, 100.0f)
        mediaPlayer.start()
    }
}

enum class TimerMode(val duration: Long) {
    POMODORO( 25 * 60 * 1000L),
    PAUSA_CURTA(5 * 60 * 1000L),
    PAUSA_LONGA(15 * 60 * 1000L),
    POMODORO_TESTE(10 * 1000L),
    PAUSA_CURTA_TESTE(5 * 1000L),
    PAUSA_LONGA_TESTE(10 * 1000L)

}

@Composable
fun PomodoroScreen(
    onStart: () -> Unit,
    onPause: () -> Unit,
    onFinish: () -> Unit
){
    var selectedMode by remember { mutableStateOf(TimerMode.POMODORO_TESTE) }
    var timeLeft by remember { mutableStateOf(TimerMode.POMODORO_TESTE.duration)}
    var isRunning by remember { mutableStateOf(false) }
    var isFirstCycleDone by remember { mutableStateOf(false) }
    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    fun startTimer(){
        if (!isRunning) {
            isRunning = true
            onStart()

            Handler(Looper.getMainLooper()).postDelayed({
                timer = object : CountDownTimer(timeLeft, 1000){
                    override fun onTick(millisUntilFinished: Long) {
                        timeLeft = millisUntilFinished
                    }

                    override fun onFinish() {
                        isRunning = false
                        isFirstCycleDone = true
                        onFinish()
                    }
                }.start()
            }, 4000)
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

    fun changeMode(mode: TimerMode) {
        timer?.cancel()
        selectedMode = mode
        timeLeft = mode.duration
        isRunning = false
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = formatTime(timeLeft), fontSize = 48.sp)
        Spacer(modifier = Modifier.height(20.dp))

        if (isFirstCycleDone){
            Row {
                Button(onClick = { changeMode(TimerMode.POMODORO) }) { Text("Pomodoro") }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = { changeMode(TimerMode.PAUSA_CURTA_TESTE) }) { Text("Pausa Curta") }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = { changeMode(TimerMode.PAUSA_LONGA_TESTE) }) { Text("Pausa Longa") }
            }
        }

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
