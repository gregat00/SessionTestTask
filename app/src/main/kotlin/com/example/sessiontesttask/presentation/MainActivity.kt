package com.example.sessiontesttask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sessiontesttask.presentation.ui.theme.SessionTestTaskTheme
import com.example.sessiontesttask.presentation.views.SessionScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SessionTestTaskTheme {
                SessionScreen()
            }
        }
    }
}