package com.example.mobile_project.model

data class Soal(
    val idSoal: Int,
    val isiSoal: String,
    val point: Int,
    val idKuis: Int,
    val tipe: String, //"pilihan", // atau "isian"
    val jawabanBenar: String,
    val opsi: List<String> = emptyList()
)
