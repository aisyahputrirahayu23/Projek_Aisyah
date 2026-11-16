package com.example.projek_aisyah.obat

data class ObatModel(
    val nama: String,
    val dosis: String,
    val bentuk: String,
    val emoji: String,
    val stok: Int,
    val satuan: String,
    val aturan: String,
    val batas: Int = 3 // stok <= batas dianggap menipis
) {
    val menipis: Boolean
        get() = stok <= batas
}