package com.example.ejercicio_en_clase_.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ejercicio_en_clase_.model.Paciente

class PacienteViewModel : ViewModel() {

    // ==================== ESTADO DE LA LISTA ====================
    var pacientes by mutableStateOf<List<Paciente>>(emptyList())
        private set

    // ==================== ESTADO DEL FORMULARIO ====================
    var nombre by mutableStateOf("")
        private set
    var especie by mutableStateOf("")
        private set
    var edad by mutableStateOf("")
        private set
    var contacto by mutableStateOf("")
        private set
    var esUrgencia by mutableStateOf(false)
        private set

    // Mensaje que se muestra si falta algún dato
    var mensajeError by mutableStateOf<String?>(null)
        private set

    private var siguienteId = 1

    // ==================== ACTUALIZAR CAMPOS ====================
    fun actualizarNombre(valor: String) {
        nombre = valor
        limpiarError()
    }

    fun actualizarEspecie(valor: String) {
        especie = valor
        limpiarError()
    }

    // Solo acepta números y máximo 3 dígitos
    fun actualizarEdad(valor: String) {
        if (valor.length <= 3 && valor.all { it.isDigit() }) {
            edad = valor
            limpiarError()
        }
    }

    fun actualizarContacto(valor: String) {
        contacto = valor
        limpiarError()
    }

    fun actualizarEsUrgencia(valor: Boolean) {
        esUrgencia = valor
    }

    // ==================== AGREGAR PACIENTE ====================
    fun agregarPaciente(
        nombre: String = this.nombre,
        especie: String = this.especie,
        edad: String = this.edad,
        contacto: String = this.contacto,
        esUrgencia: Boolean = this.esUrgencia
    ): Boolean {
        val nombreValidado = nombre.trim()
        val especieValidada = especie.trim()
        val contactoValidado = contacto.trim()
        val edadNumerica = edad.trim().toIntOrNull()

        if (nombreValidado.isEmpty()) {
            mensajeError = "El nombre del paciente es obligatorio."
            return false
        }
        if (especieValidada.isEmpty()) {
            mensajeError = "Indica la especie o raza del paciente."
            return false
        }
        if (edadNumerica == null || edadNumerica !in 0..100) {
            mensajeError = "La edad debe ser un número entre 0 y 100."
            return false
        }
        if (contactoValidado.isEmpty()) {
            mensajeError = "El contacto de emergencia es obligatorio."
            return false
        }

        pacientes = pacientes + Paciente(
            id = (siguienteId++).toString(),
            nombre = nombreValidado,
            especie = especieValidada,
            edad = edadNumerica,
            contactoEmergencia = contactoValidado,
            esUrgencia = esUrgencia
        )
        limpiarFormulario()
        return true
    }

    // ==================== LIMPIAR Y ELIMINAR ====================
    fun limpiarFormulario() {
        nombre = ""
        especie = ""
        edad = ""
        contacto = ""
        esUrgencia = false
        mensajeError = null
    }

    fun eliminarPaciente(id: String) {
        pacientes = pacientes.filterNot { it.id == id }
    }

    private fun limpiarError() {
        if (mensajeError != null) {
            mensajeError = null
        }
    }
}