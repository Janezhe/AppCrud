package com.example.appcrud

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*

@Composable
fun DialogPago(onDismiss: () -> Unit, onSave: (Pago) -> Unit) {

    var destinatario by remember { mutableStateOf("") }
    var cuenta by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Transferencia") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (destinatario.isNotEmpty() && monto.isNotEmpty()) {
                    onSave(
                        Pago(
                            0,
                            destinatario,
                            cuenta,
                            monto.toDoubleOrNull() ?: 0.0,
                            fecha,
                            tipo
                        )
                    )
                }
            }) { Text("Guardar") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancelar") }
        },
        title = { Text("Nuevo Pago") },
        text = {
            Column {
                OutlinedTextField(destinatario, { destinatario = it }, label = { Text("Destinatario") })
                OutlinedTextField(cuenta, { cuenta = it }, label = { Text("Cuenta") })
                OutlinedTextField(monto, { monto = it }, label = { Text("Monto") })
                OutlinedTextField(fecha, { fecha = it }, label = { Text("Fecha") })
                OutlinedTextField(tipo, { tipo = it }, label = { Text("Tipo") })
            }
        }
    )
}