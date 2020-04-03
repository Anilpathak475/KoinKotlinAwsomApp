package com.anil.hse.persistance.entitiy

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productName: String,
    val productId: String,
    val price: String,
    val imageUrl: String,
    var quantity: Int,
    @ColumnInfo(name = "timeStamp")
    @Nullable
    var time: Long,
    var isCheckOutDone: Boolean = false
)