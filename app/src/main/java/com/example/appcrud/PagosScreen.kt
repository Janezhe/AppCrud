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

@Composable
fun PagosScreen(jsonHelper: JsonHelperPagos, onNavigateToGastos: () -> Unit) {
    var pagos by remember { mutableStateOf(jsonHelper.leer()) }
    var showDialog by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf<Int?>(null) }

    fun guardar(lista: MutableList<Pago>) {
        jsonHelper.guardar(lista)
        pagos = lista
    }

    val total = pagos.sumOf { it.monto }

    Column(modifier = Modifier.fillMaxSize().background(FondoApp)) {

        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AzulBanco)
                .padding(20.dp)
        ) {
            Text("MIS PAGOS", color = AzulMuted, fontSize = 11.sp, letterSpacing = 0.1.sp)
            Text("Total: S/ %.2f".format(total), color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text("Total de pagos registrados", color = AzulMuted, fontSize = 11.sp)
        }

        // Botón nuevo pago
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Button(
                onClick = { editIndex = null; showDialog = true },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AzulBanco),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("＋  Nuevo Pago", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Lista pagos
        LazyColumn(
            modifier = Modifier.weight(1f).padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            itemsIndexed(pagos) { index, pago ->
                PagoCard(
                    pago = pago,
                    onEdit = { editIndex = index; showDialog = true },
                    onDelete = {
                        val nueva = pagos.toMutableList()
                        nueva.removeAt(index)
                        guardar(nueva)
                    }
                )
            }
        }

        // Bottom Navigation
        BottomNavBar(selected = "pagos", onGastos = onNavigateToGastos, onPagos = {})
    }

    if (showDialog) {
        PagoDialog(
            pago = editIndex?.let { pagos[it] },
            onDismiss = { showDialog = false },
            onGuardar = { destinatario, cuenta, monto, fecha, tipo ->
                val nueva = pagos.toMutableList()
                if (editIndex != null) {
                    nueva[editIndex!!] = Pago(
                        id = pagos[editIndex!!].id,
                        destinatario = destinatario,
                        numeroCuenta = cuenta,
                        monto = monto,
                        fecha = fecha,
                        tipoPago = tipo
                    )
                } else {
                    val newId = if (nueva.isEmpty()) 1 else nueva.maxOf { it.id } + 1
                    nueva.add(Pago(id = newId, destinatario = destinatario, numeroCuenta = cuenta, monto = monto, fecha = fecha, tipoPago = tipo))
                }
                guardar(nueva)
                showDialog = false
            }
        )
    }
}

@Composable
fun PagoCard(pago: Pago, onEdit: () -> Unit, onDelete: () -> Unit) {
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
                Text("🏦", fontSize = 20.sp)
                Text(
                    pago.destinatario,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f).padding(start = 10.dp)
                )
                Text(pago.fecha, color = AzulMuted, fontSize = 11.sp)
            }

            // Cuerpo
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Cta: ${pago.numeroCuenta}", color = Color(0xFF555555), fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = AzulBanco
                        ) {
                            Text(
                                pago.tipoPago,
                                color = Color.White,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("MONTO", color = Color(0xFFAAAAAA), fontSize = 10.sp)
                        Text(
                            "S/ %.2f".format(pago.monto),
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
}

@Composable
fun PagoDialog(
    pago: Pago?,
    onDismiss: () -> Unit,
    onGuardar: (String, String, Double, String, String) -> Unit
) {
    var destinatario by remember { mutableStateOf(pago?.destinatario ?: "") }
    var cuenta by remember { mutableStateOf(pago?.numeroCuenta ?: "") }
    var monto by remember { mutableStateOf(pago?.monto?.toString() ?: "") }
    var fecha by remember { mutableStateOf(pago?.fecha ?: "") }
    var tipo by remember { mutableStateOf(pago?.tipoPago ?: "Transferencia") }
    val context = LocalContext.current
    val dialogContext = remember(context) {
        var ctx = context
        while (ctx is android.content.ContextWrapper && ctx !is android.app.Activity) {
            ctx = ctx.baseContext
        }
        ctx
    }
    val calendario = Calendar.getInstance()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    if (pago == null) "Nuevo Pago" else "Editar Pago",
                    color = AzulBanco, fontSize = 18.sp, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text("Destinatario", color = Color(0xFF555555), fontSize = 12.sp)
                OutlinedTextField(
                    value = destinatario, onValueChange = { destinatario = it },
                    placeholder = { Text("Ej. Juan Pérez") },
                    modifier = Modifier.fillMaxWidth(), singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text("Número de cuenta", color = Color(0xFF555555), fontSize = 12.sp)
                OutlinedTextField(
                    value = cuenta, onValueChange = { cuenta = it },
                    placeholder = { Text("Ej. 00123456789") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(), singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text("Monto a pagar", color = Color(0xFF555555), fontSize = 12.sp)
                OutlinedTextField(
                    value = monto, onValueChange = { monto = it },
                    placeholder = { Text("S/ 0.00") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(), singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text("Fecha de pago", color = Color(0xFF555555), fontSize = 12.sp)
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
                                dialogContext,
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

                Spacer(modifier = Modifier.height(10.dp))

                Text("Tipo de pago", color = Color(0xFF555555), fontSize = 12.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = tipo == "Transferencia",
                        onClick = { tipo = "Transferencia" },
                        colors = RadioButtonDefaults.colors(selectedColor = AzulBanco)
                    )
                    Text("Transferencia", color = AzulBanco, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(
                        selected = tipo == "Efectivo",
                        onClick = { tipo = "Efectivo" },
                        colors = RadioButtonDefaults.colors(selectedColor = AzulBanco)
                    )
                    Text("Efectivo", color = AzulBanco, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (destinatario.isNotBlank() && cuenta.isNotBlank() && monto.isNotBlank() && fecha.isNotBlank()) {
                            onGuardar(destinatario, cuenta, monto.toDoubleOrNull() ?: 0.0, fecha, tipo)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AzulBanco),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Guardar Pago", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
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