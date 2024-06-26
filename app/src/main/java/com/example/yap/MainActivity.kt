package com.example.yap

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.yap.ui.theme.YapTheme
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : ComponentActivity() {
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        setContent {
            YapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthNavigation(mAuth)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            Toast.makeText(this, "User is logged in", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "User is logged out", Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun AuthNavigation(mAuth: FirebaseAuth?) {
    var isSignUp by remember { mutableStateOf(true) }
    var isLoggedIn by remember { mutableStateOf(false) }

    if (isLoggedIn) {
        MainPage(onLogoutClicked = {
            mAuth?.signOut()
            isLoggedIn = false
            isSignUp = true
        })
    } else {
        if (isSignUp) {
            SignUpScreen(mAuth, onSignInClicked = { isSignUp = false })
        } else {
            LoginScreen(mAuth, onSignUpClicked = { isSignUp = true }, onLoginSuccess = { isLoggedIn = true })
        }
    }
}

@Composable
fun SignUpScreen(mAuth: FirebaseAuth?, onSignInClicked: () -> Unit) {
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
            text = "Sign Up",
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
                    mAuth?.createUserWithEmailAndPassword(email, password)
                        ?.addOnCompleteListener { task: Task<AuthResult> ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_LONG).show()
                                onSignInClicked()
                            } else {
                                Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onSignInClicked) {
            Text("Already have an account? Sign In")
        }
    }
}

@Composable
fun LoginScreen(mAuth: FirebaseAuth?, onSignUpClicked: () -> Unit, onLoginSuccess: () -> Unit) {
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
                mAuth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Sign In Successful", Toast.LENGTH_LONG).show()
                            onLoginSuccess()
                        } else {
                            Toast.makeText(context, "Sign In Failed", Toast.LENGTH_LONG).show()
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

@Composable
fun MainPage(onLogoutClicked: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to YAP Messenger",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Add more UI elements for the main page here
        }
        TextButton(
            onClick = onLogoutClicked,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text("Logout")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    YapTheme {
        AuthNavigation(null)
    }
}
