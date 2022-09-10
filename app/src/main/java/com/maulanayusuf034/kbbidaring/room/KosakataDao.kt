package com.maulanayusuf034.kbbidaring.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KosakataDao {
    @Insert
    suspend fun addKosakata(kosakata: Kosakata)

    @Delete
    suspend fun deleteKosakata(kosakata: Kosakata)

    @Query("SELECT * FROM kosakata")
    suspend fun getKosakata(): List<Kosakata>
}