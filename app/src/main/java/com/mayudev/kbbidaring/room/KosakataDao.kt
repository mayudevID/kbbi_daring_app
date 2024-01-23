package com.mayudev.kbbidaring.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KosakataDao {
    @Insert
    fun addKosakata(kosakata: Kosakata)

    @Delete
    fun deleteKosakata(kosakata: Kosakata)

    @Query("SELECT * FROM kosakata")
    fun getKosakata(): List<Kosakata>?
}