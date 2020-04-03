package com.anil.hse.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anil.hse.persistance.entitiy.Cart

@Database(
    entities = [Cart::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao
}