package com.example.chronometer

import android.os.Bundle
import android.os.SystemClock
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

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

    // Общее количество прошедших секунд
    var elapsedTime by rememberSaveable {
        mutableLongStateOf(0L)
    }

    // Состояние секундомера:
    // true  - секундомер работает
    // false - секундомер остановлен
    var isRunning by rememberSaveable {
        mutableStateOf(false)
    }

    // Время, с которого начался текущий отсчёт
    var startTime by rememberSaveable {
        mutableLongStateOf(0L)
    }

    /*
     * LaunchedEffect запускает coroutine,
     * когда секундомер находится в состоянии isRunning = true.
     *
     * Каждые 100 миллисекунд пересчитываем
     * фактически прошедшее время.
     */
    LaunchedEffect(isRunning) {

        while (isRunning) {

            val currentTime = SystemClock.elapsedRealtime()

            elapsedTime =
                (currentTime - startTime) / 1000L

            delay(100L)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        // Отображение прошедшего времени
        Text(
            text = formatTime(elapsedTime)
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        // Кнопка запуска секундомера
        Button(
            onClick = {

                if (!isRunning) {

                    /*
                     * При запуске рассчитываем startTime
                     * с учётом уже накопленного времени.
                     *
                     * Это позволяет корректно продолжить
                     * отсчёт после Pause.
                     */
                    startTime =
                        SystemClock.elapsedRealtime() -
                                elapsedTime * 1000L

                    isRunning = true
                }
            }
        ) {
            Text("Start")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Кнопка приостановки секундомера
        Button(
            onClick = {

                if (isRunning) {

                    val currentTime =
                        SystemClock.elapsedRealtime()

                    /*
                     * Перед остановкой сохраняем
                     * актуальное количество секунд.
                     */
                    elapsedTime =
                        (currentTime - startTime) / 1000L

                    isRunning = false
                }
            }
        ) {
            Text("Pause")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Кнопка сброса секундомера
        Button(
            onClick = {

                isRunning = false
                elapsedTime = 0L
                startTime = 0L
            }
        ) {
            Text("Reset")
        }
    }
}


/**
 * Преобразует количество секунд
 * в формат MM:SS.
 *
 * Например:
 *
 * 0   -> 00:00
 * 5   -> 00:05
 * 65  -> 01:05
 * 125 -> 02:05
 */
fun formatTime(totalSeconds: Long): String {

    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return String.format(
        "%02d:%02d",
        minutes,
        seconds
    )
}