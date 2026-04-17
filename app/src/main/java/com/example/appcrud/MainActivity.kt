package com.example.appcrud

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.appcrud.ui.theme.AppCrudTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonHelper = JsonHelper(this)
        setContent {
            AppCrudTheme {
                GastosScreen(
                    jsonHelper = jsonHelper,
                    onNavigateToPagos = {
                        startActivity(Intent(this, PagosActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}