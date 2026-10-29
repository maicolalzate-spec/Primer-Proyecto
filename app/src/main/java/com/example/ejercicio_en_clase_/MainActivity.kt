package com.example.ejercicio_en_clase_

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ejercicio_en_clase_.ui.theme.Ejercicio_en_clase_Theme
import com.example.ejercicio_en_clase_.view.NavView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ejercicio_en_clase_Theme {
                NavView()
            }
        }
    }
}