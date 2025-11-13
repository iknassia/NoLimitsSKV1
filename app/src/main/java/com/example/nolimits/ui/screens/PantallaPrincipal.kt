// Ruta: app/src/main/java/com/example/nolimits/ui/screens/PantallaPrincipal.kt
package com.example.nolimits.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nolimits.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        // TopBar negro con texto blanco centrado
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "°-._ NoLimits _.-°",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        },

        // Footer negro con texto centrado
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "_.-°-._ All in One _.-°-._",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    ) { inner ->
        // Contenido centrado entre la barra superior y el footer
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center, // centra verticalmente
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FeatureCard(
                title = "¿Quiénes somos?",
                text = "Plataforma para películas, videojuegos y accesorios. Calidad, soporte y envíos rápidos.",
                icon = { Icon(Icons.Default.Info, contentDescription = null) }
            )

            Spacer(Modifier.height(20.dp))

            FeatureCard(
                title = "Sucursales",
                text = "Actualmente no existen sucursales físicas. Horario: Lunes-Viernes 8:00–17:00.",
                icon = { Icon(Icons.Default.LocationOn, contentDescription = null) }
            )

            Spacer(Modifier.height(20.dp))

            FeatureCard(
                title = "Soporte",
                text = "¿Dudas con tu pedido? Escríbenos: NoLimitsCorp@gmail.com o al \n+56 9 1234 5678.",
                icon = { Icon(Icons.Default.SupportAgent, contentDescription = null) }
            )

            Spacer(Modifier.height(24.dp))

            // Botones principales
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { navController.navigate(Screen.SignIn.route) },
                    modifier = Modifier.weight(1f)
                ) { Text("Iniciar sesión") }

                Button(
                    onClick = { navController.navigate(Screen.Register.route) },
                    modifier = Modifier.weight(1f)
                ) { Text("Crear cuenta") }
            }
        }
    }
}

@Composable
private fun FeatureCard(
    title: String,
    text: String,
    icon: @Composable () -> Unit
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            icon()
            Spacer(Modifier.width(12.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text(text, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}