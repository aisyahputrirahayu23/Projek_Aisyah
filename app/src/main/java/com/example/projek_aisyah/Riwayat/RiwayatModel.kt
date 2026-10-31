package com.example.projek_aisyah.Riwayat

data class RiwayatModel(
    val namaObat: String,
    val jam: String,      // contoh: "07:00"
    val jumlah: String,   // contoh: "1 tablet"
    val status: String    // "Diminum", "Tertunda", atau "Terlewat"
)