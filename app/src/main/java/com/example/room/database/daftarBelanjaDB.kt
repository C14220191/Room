package com.example.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [daftarBelanja::class, historyBarang::class], version = 2)
abstract class daftarBelanjaDB : RoomDatabase() {
    abstract fun daftarBelanjaDAO(): daftarBelanjaDAO
    abstract fun historyBarangDAO(): historyBarangDAO

    companion object {
        @Volatile
        private var INSTANCE: daftarBelanjaDB? = null

        @JvmStatic
        fun getDatabase(context: Context): daftarBelanjaDB {
            if (INSTANCE == null) {
                synchronized(daftarBelanjaDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        daftarBelanjaDB::class.java, "daftarBelanjaDB"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration() // Tambahkan ini untuk menghancurkan database lama jika ada perubahan versi
                        .build()
                }
            }
            return INSTANCE as daftarBelanjaDB
        }
    }
}
