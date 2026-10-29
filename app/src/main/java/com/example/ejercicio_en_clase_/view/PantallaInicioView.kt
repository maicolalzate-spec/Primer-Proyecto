package com.example.ejercicio_en_clase_.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ejercicio_en_clase_.model.Paciente
import com.example.ejercicio_en_clase_.viewmodel.PacienteViewModel

// Pantalla principal: registra y muestra los pacientes
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicioView(
    viewModel: PacienteViewModel = viewModel()
) {
    // La vista observa la lista; si cambia en el ViewModel, la pantalla se actualiza sola
    val listaPacientes = viewModel.pacientes

    Scaffold(
        // Los insets los maneja el Scaffold de NavView, por eso van en 0
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("KotlinPet Manager 🐾", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            FormularioPaciente(viewModel = viewModel)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Pacientes registrados (${listaPacientes.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (listaPacientes.isEmpty()) {
                Text(
                    text = "Todavía no hay pacientes registrados.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(listaPacientes, key = { it.id }) { paciente ->
                        TarjetaPaciente(
                            paciente = paciente,
                            onEliminar = { viewModel.eliminarPaciente(paciente.id) }
                        )
                    }
                }
            }
        }
    }
}

// Formulario de registro
@Composable
fun FormularioPaciente(viewModel: PacienteViewModel) {
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Nuevo paciente",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Fila 1: nombre y especie
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = viewModel.nombre,
                    onValueChange = { viewModel.actualizarNombre(it) },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = viewModel.especie,
                    onValueChange = { viewModel.actualizarEspecie(it) },
                    label = { Text("Especie/Raza") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }

            // Fila 2: edad y contacto
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = viewModel.edad,
                    onValueChange = { viewModel.actualizarEdad(it) },
                    label = { Text("Edad") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(0.7f)
                )
                OutlinedTextField(
                    value = viewModel.contacto,
                    onValueChange = { viewModel.actualizarContacto(it) },
                    label = { Text("Contacto de emergencia") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.weight(1.3f)
                )
            }

            // Checkbox de urgencia
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.esUrgencia,
                    onCheckedChange = { viewModel.actualizarEsUrgencia(it) }
                )
                Text("¿Requiere atención inmediata?")
            }

            // Mensaje de error (solo aparece si hay uno)
            if (viewModel.mensajeError != null) {
                Text(
                    text = viewModel.mensajeError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Botones
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { viewModel.limpiarFormulario() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Limpiar")
                }
                Button(
                    onClick = { viewModel.agregarPaciente() },
                    modifier = Modifier.weight(2f)
                ) {
                    Text("Registrar Paciente")
                }
            }
        }
    }
}

// Tarjeta de un paciente
@Composable
fun TarjetaPaciente(paciente: Paciente, onEliminar: () -> Unit) {
    // Si es urgencia, la tarjeta se ve rojiza
    val colorFondo = if (paciente.esUrgencia) {
        MaterialTheme.colorScheme.errorContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = colorFondo),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = paciente.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${paciente.especie} - ${paciente.edad} años",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Contacto: ${paciente.contactoEmergencia}",
                    style = MaterialTheme.typography.bodySmall
                )
                if (paciente.esUrgencia) {
                    Text(
                        text = "URGENCIA",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            IconButton(onClick = onEliminar) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar paciente"
                )
            }
        }
    }
}