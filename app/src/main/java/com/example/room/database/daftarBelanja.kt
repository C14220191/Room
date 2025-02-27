package com.example.room.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class daftarBelanja (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "tanggal")
    val tanggal: String? = null,

    @ColumnInfo(name = "item")
    val item: String? = null,

    @ColumnInfo(name = "jumlah")
    val jumlah: String? = null,

    @ColumnInfo(name = "status")
    val status: Int = 0,
)