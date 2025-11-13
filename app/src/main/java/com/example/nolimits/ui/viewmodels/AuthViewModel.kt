package com.example.nolimits.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nolimits.data.local.dao.AppUserDao
import com.example.nolimits.data.local.model.AppUser
import kotlinx.coroutines.launch

class AuthViewModel(
    private val dao: AppUserDao
) : ViewModel() {

    // Registrar usuario
    fun registrarUsuario(
        nombre: String,
        apellidos: String,
        correo: String,
        telefono: String,
        direccion: String,
        clave: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {

            // Verifica que el correo no esté registrado
            val existingUser = dao.getUserByCorreo(correo)
            if (existingUser != null) {
                onError("El correo ya está registrado.")
                return@launch
            }

            val newUser = AppUser(
                nombre = nombre,
                apellidos = apellidos,
                correo = correo,
                telefono = telefono,
                direccion = direccion,
                clave = clave
            )

            dao.insertUser(newUser)
            onSuccess()
        }
    }

    // Iniciar sesión
    fun iniciarSesion(
        correo: String,
        clave: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val user = dao.login(correo, clave)
            onResult(user != null)
        }
    }
}
