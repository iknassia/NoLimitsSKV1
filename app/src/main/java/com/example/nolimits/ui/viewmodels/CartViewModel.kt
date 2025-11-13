package com.example.nolimits.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.nolimits.domain.Product

// ViewModel encargado de manejar la lógica del carrito de compras
class CartViewModel : ViewModel() {
    // Lista observable de productos agregados al carrito.
    // 'mutableStateListOf' permite que Jetpack Compose observe los cambios y
    // pueda actualizar automáticamente la interfaz cuando se agregan o eliminan productos.
    private val _cartItems = mutableStateListOf<Product>()

    // Expone la lista como inmutable (osea, solo lectura) hacia el exterior.
    // Esto evita que otras clases modifiquen directamente el contenido.
    val cartItems: List<Product> get() = _cartItems

    // Agregar producto al carrito
    // Se usa cuando el usuario presiona "Agregar al carrito" en el catálogo.
    fun addToCart(product: Product) {
        _cartItems.add(product)
    }

    // Eliminar producto del carrito.
    // Se llama cuando el usuario presiona "Eliminar" desde la pantalla del carrito.
    fun removeFromCart(product: Product) {
        _cartItems.remove(product)
    }

    // Calcular el total del carrito sumando el precio de todos los productos.
    // Se usa para mostrar el monto total antes de finalizar la compra.
    fun getTotal(): Double = _cartItems.sumOf { it.price }

    // Vacía el carrito completamente.
    // Se ejecuta al finalizar una compra (por ejemplo cuando se muestra la boleta).
    fun clearCart() {
        _cartItems.clear()
    }

}