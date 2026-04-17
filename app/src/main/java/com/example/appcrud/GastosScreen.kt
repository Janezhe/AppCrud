package com.example.appcrud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import android.app.DatePickerDialog
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

val AzulBanco = Color(0xFF1A3A5C)
val AzulClaro = Color(0xFFE8EEF4)
val AzulMuted = Color(0xFF8BAFC9)
val FondoApp = Color(0xFFF0F4F8)
val VerdeMontoG = Color(0xFF1A7A3C)
val RojoEliminar = Color(0xFFC62828)

@Composable
fun GastosScreen(jsonHelper: JsonHelper, onNavigateToPagos: () -> Unit) {
    var gastos by remember { mutableStateOf(jsonHelper.leer()) }
    var showDialog by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf<Int?>(null) }

    fun guardar(lista: MutableList<Gasto>) {
        jsonHelper.guardar(lista)
        gastos = lista
    }

    val total = gastos.sumOf { it.monto }

    Column(modifier = Modifier.fillMaxSize().background(FondoApp)) {

        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AzulBanco)
                .padding(20.dp)
        ) {
            Text("MIS MOVIMIENTOS", color = AzulMuted, fontSize = 11.sp, letterSpacing = 0.1.sp)
            Text("Total: S/ %.2f".format(total), color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text("Saldo disponible", color = AzulMuted, fontSize = 11.sp)
        }

        // Botón nuevo movimiento
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Button(
                onClick = { editIndex = null; showDialog = true },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AzulBanco),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("＋  Nuevo Movimiento", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
        var deleteIndex by remember { mutableStateOf<Int?>(null) }
        // Lista gastos
        LazyColumn(
            modifier = Modifier.weight(1f).padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            itemsIndexed(gastos) { index, gasto ->
                GastoCard(
                    gasto = gasto,
                    onEdit = { editIndex = index; showDialog = true },
                    onDelete = {
                        deleteIndex = index }
                )
            }
        }
        if (deleteIndex != null) {
            AlertDialog(
                onDismissRequest = { deleteIndex = null },
                icon = { Text("🗑", fontSize = 28.sp) },
                title = {
                    Text(
                        "¿Eliminar gasto?",
                        color = AzulBanco,
                        fontWeight = FontWeight.Bold
                    )
                },text = {
                    Text(
                        "Estás a punto de eliminar \"${gastos[deleteIndex!!].descripcion}\". Esta acción no se puede deshacer.",
                        color = Color(0xFF555555),
                        fontSize = 14.sp
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val nueva = gastos.toMutableList()
                            nueva.removeAt(deleteIndex!!)
                            guardar(nueva)
                            deleteIndex = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RojoEliminar),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Sí, eliminar", color = Color.White)
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { deleteIndex = null },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AzulBanco)
                    ) {
                        Text("Cancelar")
                    }
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Bottom Navigation
        BottomNavBar(selected = "gastos", onGastos = {}, onPagos = onNavigateToPagos)
    }

    if (showDialog) {
        GastoDialog(
            gasto = editIndex?.let { gastos[it] },
            onDismiss = { showDialog = false },
            onGuardar = { desc, monto, fecha ->
                val nueva = gastos.toMutableList()
                if (editIndex != null) {
                    nueva[editIndex!!] = Gasto(
                        id = gastos[editIndex!!].id,
                        descripcion = desc,
                        monto = monto,
                        fecha = fecha
                    )
                } else {
                    val newId = if (nueva.isEmpty()) 1 else nueva.maxOf { it.id } + 1
                    nueva.add(Gasto(id = newId, descripcion = desc, monto = monto, fecha = fecha))
                }
                guardar(nueva)
                showDialog = false
            }
        )
    }
}

@Composable
fun GastoCard(gasto: Gasto, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Franja header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AzulBanco)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("💳", fontSize = 20.sp)
                Text(
                    gasto.descripcion,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f).padding(start = 10.dp)
                )
                Text(gasto.fecha, color = AzulMuted, fontSize = 11.sp)
            }

            // Cuerpo
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("MONTO", color = Color(0xFFAAAAAA), fontSize = 10.sp, letterSpacing = 0.1.sp)
                    Text(
                        "S/ %.2f".format(gasto.monto),
                        color = VerdeMontoG,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Button(
                        onClick = onEdit,
                        modifier = Modifier.width(100.dp).height(36.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AzulClaro),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text("✏ Editar", color = AzulBanco, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = onDelete,
                        modifier = Modifier.width(100.dp).height(36.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RojoEliminar),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text("🗑 Eliminar", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun GastoDialog(gasto: Gasto?, onDismiss: () -> Unit, onGuardar: (String, Double, String) -> Unit) {
    var desc by remember { mutableStateOf(gasto?.descripcion ?: "") }
    var monto by remember { mutableStateOf(gasto?.monto?.toString() ?: "") }
    var fecha by remember { mutableStateOf(gasto?.fecha ?: "") }

    val context = LocalContext.current
    val calendario = Calendar.getInstance()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    if (gasto == null) "Nuevo Gasto" else "Editar Gasto",
                    color = AzulBanco,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text("Descripción", color = Color(0xFF555555), fontSize = 12.sp)
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    placeholder = { Text("Ej. Compra supermercado") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text("Monto", color = Color(0xFF555555), fontSize = 12.sp)
                OutlinedTextField(
                    value = monto,
                    onValueChange = { monto = it },
                    placeholder = { Text("S/ 0.00") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text("Fecha", color = Color(0xFF555555), fontSize = 12.sp)
                OutlinedTextField(
                    value = fecha,
                    onValueChange = {},
                    placeholder = { Text("dd/mm/aaaa") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            DatePickerDialog(
                                context,
                                { _, anio, mes, dia ->
                                    fecha = "%02d/%02d/%04d".format(dia, mes + 1, anio)
                                },
                                calendario.get(Calendar.YEAR),
                                calendario.get(Calendar.MONTH),
                                calendario.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        }) {
                            Text("📅", fontSize = 18.sp)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (desc.isNotBlank() && monto.isNotBlank() && fecha.isNotBlank()) {
                            onGuardar(desc, monto.toDoubleOrNull() ?: 0.0, fecha)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AzulBanco),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Guardar Gasto", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AzulBanco)
                ) {
                    Text("Cancelar", fontSize = 15.sp)
                }
            }
        }
    }
}