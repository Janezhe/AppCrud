package com.example.appcrud

data class Pago(
    var id: Int = 0,
    var destinatario: String = "",
    var numeroCuenta: String = "",
    var monto: Double = 0.0,
    var fecha: String = "",
    var tipoPago: String = "Transferencia"

)
