package com.example.unit4_pathway2_lunchtray

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.unit4_pathway2_lunchtray.ui.theme.LunchTrayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LunchTrayTheme {
                LunchTrayApp()
            }
        }
    }
}
