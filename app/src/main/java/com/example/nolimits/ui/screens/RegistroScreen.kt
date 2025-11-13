package com.example.nolimits.ui.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nolimits.ui.viewmodels.UsuarioViewModel
import com.example.nolimits.ui.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel,
    authViewModel: AuthViewModel
) {
    val estado by viewModel.estado.collectAsState()
    val context = LocalContext.current

    var apellidos by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    var repetirClave by rememberSaveable { mutableStateOf("") }

    var showPass by rememberSaveable { mutableStateOf(false) }
    var showPass2 by rememberSaveable { mutableStateOf(false) }

    val scroll = rememberScrollState()

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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
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
    ) { inner ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = inner.calculateTopPadding() + 8.dp,
                    bottom = inner.calculateBottomPadding() + 8.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .verticalScroll(scroll),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Nombre
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = {
                    if (it.all { c -> c.isLetter() || c.isWhitespace() })
                        viewModel.onNombreChange(it)
                },
                label = { Text("Nombre") },
                isError = estado.errores.nombre != null,
                supportingText = {
                    estado.errores.nombre?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            // Apellidos
            OutlinedTextField(
                value = apellidos,
                onValueChange = {
                    if (it.all { c -> c.isLetter() || c.isWhitespace() })
                        apellidos = it
                },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            // Correo
            OutlinedTextField(
                value = estado.correo,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("Correo electrónico") },
                isError = estado.errores.correo != null,
                supportingText = {
                    estado.errores.correo?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            // Teléfono
            OutlinedTextField(
                value = telefono,
                onValueChange = {
                    if (it.all { c -> c.isDigit() })
                        telefono = it
                },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            // Dirección
            OutlinedTextField(
                value = estado.direccion,
                onValueChange = viewModel::onDireccionChange,
                label = { Text("Dirección") },
                isError = estado.errores.direccion != null,
                supportingText = {
                    estado.errores.direccion?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            // Contraseña
            OutlinedTextField(
                value = estado.clave,
                onValueChange = viewModel::onClaveChange,
                label = { Text("Contraseña (mín. 8 caracteres)") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPass = !showPass }) {
                        Icon(
                            if (showPass) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (showPass) "Ocultar" else "Mostrar"
                        )
                    }
                },
                isError = estado.errores.clave != null,
                supportingText = {
                    estado.errores.clave?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )
            )

            // Repetir contraseña
            OutlinedTextField(
                value = repetirClave,
                onValueChange = { repetirClave = it },
                label = { Text("Repetir contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (showPass2) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPass2 = !showPass2 }) {
                        Icon(
                            if (showPass2) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (showPass2) "Ocultar" else "Mostrar"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            // Aceptar términos
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = estado.aceptaTerminos,
                    onCheckedChange = viewModel::onAceptarTerminosChange
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Acepto los términos y condiciones",
                    modifier = Modifier.clickable {
                        viewModel.onAceptarTerminosChange(!estado.aceptaTerminos)
                    }
                )
            }

            // Botón registrar (Room)
            Button(
                onClick = {
                    val ok = validarFormularioLocal(
                        nombre = estado.nombre,
                        apellidos = apellidos,
                        correo = estado.correo,
                        telefono = telefono,
                        clave = estado.clave,
                        repetirClave = repetirClave,
                        aceptaTerminos = estado.aceptaTerminos
                    )

                    if (!ok.success) {
                        Toast.makeText(context, ok.message, Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    authViewModel.registrarUsuario(
                        nombre = estado.nombre,
                        apellidos = apellidos,
                        correo = estado.correo,
                        telefono = telefono,
                        direccion = estado.direccion,
                        clave = estado.clave,
                        onSuccess = {
                            Toast.makeText(context, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                            navController.navigate("signin") {
                                popUpTo("registro") { inclusive = true }
                            }
                        },
                        onError = { msg ->
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(text = "¿Ya tienes una cuenta?")
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "Iniciar sesión",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("signin")
                    }
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

/* === FUNCIONES FUERA DEL COMPOSABLE === */

private data class CheckResult(val success: Boolean, val message: String)

private fun validarFormularioLocal(
    nombre: String,
    apellidos: String,
    correo: String,
    telefono: String,
    clave: String,
    repetirClave: String,
    aceptaTerminos: Boolean
): CheckResult {
    if (nombre.isBlank() || apellidos.isBlank() || correo.isBlank() ||
        telefono.isBlank() || clave.isBlank() || repetirClave.isBlank()
    ) {
        return CheckResult(false, "Por favor, completa todos los campos.")
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
        return CheckResult(false, "Correo electrónico inválido.")
    }
    if (telefono.length !in 8..12) {
        return CheckResult(false, "Teléfono inválido.")
    }
    if (clave.length < 8) {
        return CheckResult(false, "La contraseña debe tener al menos 8 caracteres.")
    }
    if (clave != repetirClave) {
        return CheckResult(false, "Las contraseñas no coinciden.")
    }
    if (!aceptaTerminos) {
        return CheckResult(false, "Debes aceptar los términos y condiciones.")
    }
    return CheckResult(true, "OK")
}
