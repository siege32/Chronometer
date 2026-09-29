package com.example.chronometer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChronometerScreen()
        }
    }
}

@Composable
fun ChronometerScreen() {
    var elapsedTime by remember {
        mutableLongStateOf(0L)
    }

    var isRunning by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatTime(elapsedTime)
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = {}
        ) {
            Text("Start")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {}
        ) {
            Text("Pause")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {}
        ) {
            Text("Reset")
        }
    }
}

fun formatTime(totalSeconds: Long): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return String.format(
        "%02d:%02d",
        minutes,
        seconds
    )
}