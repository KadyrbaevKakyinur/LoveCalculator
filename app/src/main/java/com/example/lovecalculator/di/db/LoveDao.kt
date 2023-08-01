package com.example.lovecalculator.di.db

import androidx.room.*
import com.example.lovecalculator.remote.LoveModel

@Dao
interface LoveDao {
    @Insert
    fun insert(loveModel: LoveModel)

    @Query("SELECT * FROM loveModel")
    fun getAll(): List<LoveModel>

    @Query("SELECT * FROM loveModel ORDER BY firstName ASC")
    fun getAllDataAlphabetically(): List<LoveModel>

    @Update
    fun update(loveModel: LoveModel)

    @Delete
    fun delete(loveModel: LoveModel)
}