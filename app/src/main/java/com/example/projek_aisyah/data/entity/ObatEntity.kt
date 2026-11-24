package com.example.projek_aisyah.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "obat")
data class ObatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val dosis: String,
    val bentuk: String,
    val aturan: String,
    val frekuensi: Int,
    val jam: String,
    val stok: Int
)