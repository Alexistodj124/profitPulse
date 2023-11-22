package com.uvg.profitpulse.Model

import java.util.Date

data class Recordatorios (
    val key: String? = null,
    val recordatorioDate: Date = Date(),
    val recordatorioDescripcion: String = "",
    val uid: String = ""
)