// Product.kt
// Modelo con atributos.

package com.example.nolimits.domain

import androidx.annotation.DrawableRes

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    @DrawableRes val imageRes: Int? = null
)