package com.example.room.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface historyBarangDAO {
    @Insert
    suspend fun insert(history: historyBarang)

    @Query("SELECT * FROM historyBarang")
    suspend fun selectAll(): List<historyBarang>
}
