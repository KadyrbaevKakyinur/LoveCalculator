package com.example.lovecalculator.di.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lovecalculator.di.db.LoveDao
import com.example.lovecalculator.remote.LoveModel

@Database(entities = [LoveModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun LoveDao(): LoveDao
}