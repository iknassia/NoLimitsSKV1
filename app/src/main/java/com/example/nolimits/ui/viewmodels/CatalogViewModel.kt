package com.example.nolimits.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nolimits.data.ProductRepository
import com.example.nolimits.domain.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CatalogViewModel : ViewModel() {
    private val repo = ProductRepository()

    private val _products = MutableStateFlow(repo.getProducts())
    val products: StateFlow<List<Product>> = _products
}