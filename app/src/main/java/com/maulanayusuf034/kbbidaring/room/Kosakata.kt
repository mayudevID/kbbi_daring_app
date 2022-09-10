package com.maulanayusuf034.kbbidaring.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Kosakata(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val vocab: String,
    val lema: String,
    val arti: List<String>
)
