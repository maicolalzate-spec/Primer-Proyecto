package com.example.ejercicio_en_clase_.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

// Opciones de la barra de navegación inferior
sealed class ItemsMenu(
    val ruta: String,      // nombre interno de la pantalla
    val titulo: String,    // texto que se ve en la barra
    val icono: ImageVector // ícono de la opción
) {
    object inicio : ItemsMenu("inicio", "Pacientes", Icons.Default.Home)
    object citas : ItemsMenu("Citas", "Citas", Icons.Default.DateRange)
    object Ajustes : ItemsMenu("Ajustes", "Ajustes", Icons.Default.Settings)
}