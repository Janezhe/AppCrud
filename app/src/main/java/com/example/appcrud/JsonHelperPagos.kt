package com.example.appcrud

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonHelperPagos(private val context: Context) {
    private val fileName = "pagos.json"
    private val gson = Gson()

    fun guardar(lista: List<Pago>) {
        val json = gson.toJson(lista)
        context.openFileOutput(fileName, Context.MODE_PRIVATE)
            .use { it.write(json.toByteArray()) }
    }

    fun leer(): MutableList<Pago> {
        return try {
            val json = context.openFileInput(fileName)
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<MutableList<Pago>>() {}.type
            gson.fromJson(json, type) ?: mutableListOf()

        } catch (e: Exception) {
            mutableListOf()
        }
    }
}