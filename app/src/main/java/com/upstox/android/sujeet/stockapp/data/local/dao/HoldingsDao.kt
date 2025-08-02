package com.upstox.android.sujeet.stockapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upstox.android.sujeet.stockapp.data.local.entity.HoldingEntity

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
@Dao
interface HoldingsDao {
    @Query("SELECT * FROM holdings")
    suspend fun getAll(): List<HoldingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(holdings: List<HoldingEntity>)
}