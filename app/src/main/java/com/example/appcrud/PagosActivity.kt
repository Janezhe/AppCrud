package com.example.appcrud

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.appcrud.ui.theme.AppCrudTheme

class PagosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonHelper = JsonHelperPagos(this)
        setContent {
            AppCrudTheme {
                PagosScreen(
                    jsonHelper = jsonHelper,
                    onNavigateToGastos = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}