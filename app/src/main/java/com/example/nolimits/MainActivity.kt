package com.example.nolimits

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.nolimits.navigation.AppNavigation
import com.example.nolimits.navigation.NavigationEvent
import com.example.nolimits.ui.theme.NoLimitsTheme
import com.example.nolimits.ui.viewmodels.CartViewModel
import com.example.nolimits.ui.viewmodels.MainViewModel
import com.example.nolimits.ui.viewmodels.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoLimitsTheme {
                // ViewModels y NavController (scope: Activity → comparten estado entre pantallas)
                val mainViewModel: MainViewModel = viewModel()
                val usuarioViewModel: UsuarioViewModel = viewModel()
                val cartViewModel: CartViewModel = viewModel()
                val navController = rememberNavController()

                // Colector de eventos de navegación del MainViewModel
                LaunchedEffect(Unit) {
                    mainViewModel.navigationEvents.collectLatest { event ->
                        when (event) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(event.route.route) {
                                    event.popUpToRoute?.let {
                                        popUpTo(it.route) { inclusive = event.inclusive }
                                    }
                                    launchSingleTop = event.singleTop
                                    restoreState = true
                                }
                            }
                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                            is NavigationEvent.NavigateUp -> navController.navigateUp()
                        }
                    }
                }

                // Grafo de navegación
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        viewModel = usuarioViewModel,
                        mainViewModel = mainViewModel,
                        cartViewModel = cartViewModel,
                        startAtPrincipal = true
                    )
                }
            }
        }
    }
}