// ProductRepository.kt
package com.example.nolimits.data

import com.example.nolimits.R
import com.example.nolimits.domain.Product

class ProductRepository {
    fun getProducts(): List<Product> = listOf(
        // Películas.
        Product(1, "Spider-Man", 12990.0, R.drawable.pspiderman1),
        Product(2, "Spider-Man 2", 13990.0, R.drawable.pspiderman2),
        Product(3, "Spider-Man 3", 11990.0, R.drawable.pspiderman3),
        // Videojuegos.
        Product(4, "Marvel's Spider-Man Remastered", 69990.0, R.drawable.vgspiderman1),
        Product(5, "Marvel’s Spider-Man: Miles Morales", 59990.0, R.drawable.vgspidermanmm),
        Product(6, "Marvel’s Spider-Man 2", 72990.0, R.drawable.vgspiderman2),
        //Accesorios.
        Product(7, "Máscara Electrónica de Spider-Man", 59990.0, R.drawable.accspiderman1),
        Product(8, "DualSense Spider-Man", 139990.0, R.drawable.accspiderman2),
        Product(9, "Audífonos Xtech Spiderman WRD LED", 29990.0, R.drawable.accspiderman3)
    )
}