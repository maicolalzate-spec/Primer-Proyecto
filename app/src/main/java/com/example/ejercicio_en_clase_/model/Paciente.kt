package com.example.ejercicio_en_clase_.model

data class Paciente(
    val id: String,
    val nombre: String,
    val especie: String,
    val edad: Int,
    val contactoEmergencia: String = "",
    val esUrgencia: Boolean = false
)