package com.example.nolimits.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nolimits.data.local.database.AppDatabase
import com.example.nolimits.ui.screens.*
import com.example.nolimits.ui.viewmodels.*
import com.example.nolimits.domain.Product

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: UsuarioViewModel,
    mainViewModel: MainViewModel,
    cartViewModel: CartViewModel,
    startAtPrincipal: Boolean = false
) {

    // ─────────── ROOM + AUTHVIEWMODEL ───────────
    val context = LocalContext.current

    // Base de datos
    val db = AppDatabase.getDatabase(context)

    // DAO
    val appUserDao = db.appUserDao()

    // ViewModel de autenticación
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(appUserDao)
    )

    // ─────────────────────────────────────────────

    NavHost(
        navController = navController,
        startDestination = if (startAtPrincipal) Screen.PantallaPrincipal.route else Screen.SignIn.route
    ) {

        // ──────────────── PANTALLA PRINCIPAL ────────────────
        composable(Screen.PantallaPrincipal.route) {
            PantallaPrincipal(navController = navController)
        }

        // ──────────────── AUTENTICACIÓN ────────────────

        composable(Screen.SignIn.route) {
            SignInScreen(
                navController = navController,
                authViewModel = authViewModel       // ← AGREGADO
            )
        }

        composable(Screen.Register.route) {
            RegistroScreen(
                navController = navController,
                viewModel = viewModel,
                authViewModel = authViewModel        // ← AGREGADO
            )
        }

        composable(Screen.Recover.route) {
            RecuperarContrasenaScreen(navController = navController)
        }

        // ──────────────── CATÁLOGO Y CARRITO ────────────────
        composable(Screen.Catalog.route) {
            CatalogScreen(navController = navController, cartViewModel = cartViewModel)
        }

        composable(Screen.Cart.route) {
            CartScreen(navController = navController, cartViewModel = cartViewModel)
        }

        // ──────────────── MÉTODO DE PAGO ────────────────
        composable(Screen.MetodoPago.route) {
            PantallaMetodoPago(navController = navController, cartViewModel = cartViewModel)
        }

        // ──────────────── BOLETA DE COMPRA ────────────────
        composable("boleta") { backStackEntry ->
            val items = backStackEntry.savedStateHandle.get<List<Product>>("itemsComprados") ?: emptyList()
            val total = backStackEntry.savedStateHandle.get<Double>("total") ?: 0.0
            val ultimos4 = backStackEntry.savedStateHandle.get<String>("ultimos4")
            BoletaComprobante(
                navController = navController,
                itemsComprados = items,
                total = total,
                ultimos4 = ultimos4
            )
        }

        // ──────────────── RESUMEN DEL REGISTRO ────────────────
        composable(Screen.Summary.route) {
            ResumenScreen(viewModel = viewModel)
        }
    }
}
