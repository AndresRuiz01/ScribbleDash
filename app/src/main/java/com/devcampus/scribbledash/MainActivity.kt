package com.devcampus.scribbledash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.devcampus.core.presentation.design_system.ScribbleDashTheme
import com.devcampus.scribbledash.navigation.NavigationRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ScribbleDashTheme {
                NavigationRoot()
            }
        }
    }
}