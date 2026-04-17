package com.example.appcrud

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*

@Composable
fun DialogGasto(onDismiss: () -> Unit, onSave: (Gasto) -> Unit) {

    var desc by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (desc.isNotEmpty() && monto.isNotEmpty()) {
                    onSave(Gasto(0, desc, monto.toDoubleOrNull() ?: 0.0, fecha))
                }
            }) { Text("Guardar") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancelar") }
        },
        title = { Text("Nuevo Gasto") },
        text = {
            Column {
                OutlinedTextField(desc, { desc = it }, label = { Text("Descripción") })
                OutlinedTextField(monto, { monto = it }, label = { Text("Monto") })
                OutlinedTextField(fecha, { fecha = it }, label = { Text("Fecha") })
            }
        }
    )
}