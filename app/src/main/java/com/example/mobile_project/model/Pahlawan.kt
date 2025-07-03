package com.example.mobile_project.model

import java.io.Serializable

data class Pahlawan(
    val idPahlawan: Int,
    val namaPahlawan: String,
    val tempatLahir: String,
    val tanggalLahir: String,
    val julukan: String,
    val tokoh: String,
    val foto: String,
    val cerita: String,
    val audio: String,
    var isLearned: Int
) : Serializable
