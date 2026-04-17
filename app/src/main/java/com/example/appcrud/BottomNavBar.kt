package com.example.appcrud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavBar(selected: String, onGastos: () -> Unit, onPagos: () -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp
    ) {
        NavigationBarItem(
            selected = selected == "gastos",
            onClick = onGastos,
            icon = {
                Box(
                    modifier = Modifier
                        .background(
                            if (selected == "gastos") AzulClaro else Color.Transparent,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.AccountBalanceWallet,
                        contentDescription = "Gastos",
                        tint = if (selected == "gastos") AzulBanco else Color.Gray
                    )
                }
            },
            label = { Text("Gastos", color = if (selected == "gastos") AzulBanco else Color.Gray) },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            selected = selected == "pagos",
            onClick = onPagos,
            icon = {
                Box(
                    modifier = Modifier
                        .background(
                            if (selected == "pagos") AzulClaro else Color.Transparent,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Payment,
                        contentDescription = "Pagos",
                        tint = if (selected == "pagos") AzulBanco else Color.Gray
                    )
                }
            },
            label = { Text("Pagos", color = if (selected == "pagos") AzulBanco else Color.Gray) },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )
    }
}