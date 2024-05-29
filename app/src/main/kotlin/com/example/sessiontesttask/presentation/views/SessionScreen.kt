package com.example.sessiontesttask.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.sessiontesttask.presentation.viewmodels.SessionViewModel
import org.koin.androidx.compose.get

@Composable
fun SessionScreen(
    modifier: Modifier = Modifier,
    viewModel: SessionViewModel = get(),
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val sessionState by viewModel.sessionFlow.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Session ${sessionState.sessionCount}", fontSize = sessionState.textSize)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> viewModel.unregisterSensorListener()
                Lifecycle.Event.ON_RESUME -> viewModel.registerSensorListener()
                Lifecycle.Event.ON_START -> viewModel.updateSession()
                Lifecycle.Event.ON_STOP -> viewModel.saveOutTime()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}