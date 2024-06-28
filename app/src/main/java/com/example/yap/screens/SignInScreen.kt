package com.example.yap.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

@Composable
fun SignInScreen(mAuth: FirebaseAuth?, onSignUpClicked: () -> Unit, onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign In",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Please enter email and password", Toast.LENGTH_LONG).show()
                } else {
                    mAuth?.signInWithEmailAndPassword(email, password)
                        ?.addOnCompleteListener { signInTask: Task<AuthResult> ->
                            if (signInTask.isSuccessful) {
                                Toast.makeText(context, "Sign In Successful", Toast.LENGTH_LONG).show()
                                onLoginSuccess()
                            } else {
                                val exception = signInTask.exception
                                when (exception) {
                                    is FirebaseAuthInvalidCredentialsException -> {
                                        Toast.makeText(context, "Invalid password. Please try again.", Toast.LENGTH_LONG).show()
                                    }
                                    is FirebaseAuthInvalidUserException -> {
                                        Toast.makeText(context, "Email not found, redirecting to sign up", Toast.LENGTH_LONG).show()
                                        onSignUpClicked()
                                    }
                                    else -> {
                                        Toast.makeText(context, "Sign In Failed: ${exception?.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onSignUpClicked) {
            Text("Don't have an account? Sign Up")
        }
    }
}
