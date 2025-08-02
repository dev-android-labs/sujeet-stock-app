package com.upstox.android.sujeet.stockapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.upstox.android.sujeet.stockapp.data.local.dao.HoldingsDao
import com.upstox.android.sujeet.stockapp.data.local.entity.HoldingEntity

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
@Database(entities = [HoldingEntity::class], version = 1, exportSchema = false)
abstract class HoldingsDatabase : RoomDatabase() {
    abstract fun holdingsDao(): HoldingsDao
}