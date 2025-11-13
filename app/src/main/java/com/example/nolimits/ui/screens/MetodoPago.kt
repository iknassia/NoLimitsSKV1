package com.example.nolimits.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nolimits.domain.Product
import com.example.nolimits.navigation.Screen
import com.example.nolimits.ui.viewmodels.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMetodoPago(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val foco = LocalFocusManager.current

    var nombreTitular by rememberSaveable { mutableStateOf("") }
    var numeroTarjeta by rememberSaveable { mutableStateOf("") }
    var fechaVencimiento by rememberSaveable { mutableStateOf("") }
    var codigoCvv by rememberSaveable { mutableStateOf("") }
    var guardarTarjeta by rememberSaveable { mutableStateOf(true) }

    val total = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Double>("total") ?: cartViewModel.getTotal()

    val nombreValido = nombreTitular.trim().contains(" ") && nombreTitular.trim().length >= 6
    val digitosTarjeta = numeroTarjeta.filter { it.isDigit() }
    val numeroValido = digitosTarjeta.length == 16
    val vencimientoValido = run {
        val p = fechaVencimiento.split("/")
        if (p.size == 2) {
            val mm = p[0].toIntOrNull()
            val aa = p[1].toIntOrNull()
            (mm != null && mm in 1..12 && aa != null && aa in 0..99)
        } else false
    }
    val cvvValido = codigoCvv.length in 3..4 && codigoCvv.all { it.isDigit() }
    val formularioValido = nombreValido && numeroValido && vencimientoValido && cvvValido

    fun formatearTarjeta(s: String) = s.filter { it.isDigit() }.chunked(4).joinToString(" ")
    fun formatearVenc(s: String): String {
        val d = s.filter { it.isDigit() }.take(4)
        return buildString {
            for (i in d.indices) {
                append(d[i])
                if (i == 1 && d.length > 2) append("/")
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "°-._ NoLimits _.-°",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Cart.route) {
                                popUpTo(Screen.MetodoPago.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver al carrito",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
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
    ) { pad ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text("Total: $${"%,.0f".format(total)}", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = nombreTitular,
                    onValueChange = { nombreTitular = it },
                    label = { Text("Nombre del titular") },
                    singleLine = true,
                    isError = nombreTitular.isNotBlank() && !nombreValido,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formatearTarjeta(numeroTarjeta),
                    onValueChange = { numeroTarjeta = it },
                    label = { Text("Número de tarjeta (16 dígitos)") },
                    singleLine = true,
                    isError = numeroTarjeta.isNotBlank() && !numeroValido,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = formatearVenc(fechaVencimiento),
                        onValueChange = { fechaVencimiento = it },
                        label = { Text("Vencimiento (MM/AA)") },
                        singleLine = true,
                        isError = fechaVencimiento.isNotBlank() && !vencimientoValido,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = codigoCvv,
                        onValueChange = { codigoCvv = it.filter { c -> c.isDigit() }.take(4) },
                        label = { Text("CVV") },
                        singleLine = true,
                        isError = codigoCvv.isNotBlank() && !cvvValido,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { foco.clearFocus() }),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = guardarTarjeta, onCheckedChange = { guardarTarjeta = it })
                    Text("Guardar tarjeta para próximas compras (simulación)")
                }

                Button(
                    onClick = {
                        val items = navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.get<List<Product>>("itemsComprados")
                            ?: emptyList()

                        navController.navigate("boleta") {
                            launchSingleTop = true
                        }
                        navController.currentBackStackEntry?.savedStateHandle?.set("itemsComprados", items)
                        navController.currentBackStackEntry?.savedStateHandle?.set("total", total)
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "ultimos4",
                            digitosTarjeta.takeLast(4)
                        )

                        cartViewModel.clearCart()
                    },
                    enabled = formularioValido,
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Confirmar y pagar") }

                if (!formularioValido) {
                    Text(
                        "Completa todos los campos correctamente para continuar.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}