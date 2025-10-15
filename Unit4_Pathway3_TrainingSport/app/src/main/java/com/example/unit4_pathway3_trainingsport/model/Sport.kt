package com.example.unit4_pathway3_trainingsport.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Data model for Sport
 */
data class Sport(
    val id: Int,
    val titleResourceId: Int,
    val subtitleResourceId: Int,
    val playerCount: Int,
    val olympic: Boolean,
    val imageResourceId: Int,
    val sportsImageBanner: Int,
    val sportDetails: Int,
    val caloriesPerHour: Int = 0,
    val hoursPerWeek: Int = 0
){
    val totalCaloriesPerWeek: Int
        get() = caloriesPerHour * hoursPerWeek
}
