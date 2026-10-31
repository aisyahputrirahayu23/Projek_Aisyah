package com.example.projek_aisyah.Jadwal

data class JadwalModel(
    val namaObat: String,
    val dosis: String,
    val frekuensi: Int,        // berapa kali sehari
    val jadwal: String,        // contoh: "07:00 & 19:00"
    val stok: Int,             // sisa stok sekarang
    val stokAwal: Int,         // stok saat awal, untuk hitung progres bar
    val gambarObat: Int,
    val sampai: String? = null // tanggal selesai pengobatan, boleh kosong
)