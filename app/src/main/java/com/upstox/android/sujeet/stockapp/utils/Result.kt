package com.upstox.android.sujeet.stockapp.utils

/**
 * Created by SUJEET KUMAR on 8/2/2025
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}