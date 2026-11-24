package com.example.projek_aisyah.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.projek_aisyah.data.entity.ObatEntity

@Dao
interface ObatDao {

    @Query("SELECT * FROM obat ORDER BY jam ASC")
    suspend fun getAll(): List<ObatEntity>

    @Insert
    suspend fun insert(obat: ObatEntity)

    @Delete
    suspend fun delete(obat: ObatEntity)

    // Untuk badge "stok menipis" (stok <= 3, sama dengan aturan di adapter)
    @Query("SELECT COUNT(*) FROM obat WHERE stok <= 3")
    suspend fun countMenipis(): Int
}