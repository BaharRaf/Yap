package com.example.yap

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.yap.screens.MainPage
import com.example.yap.screens.SignInScreen
import com.example.yap.screens.SignUpScreen
import com.example.yap.ui.theme.YapTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        setContent {
            YapTheme {
                AuthNavigation(mAuth)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth?.currentUser?.let {
            Toast.makeText(this, "User is Signed in", Toast.LENGTH_LONG).show()
        } ?: run {
            Toast.makeText(this, "User is Signed out", Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun AuthNavigation(mAuth: FirebaseAuth?) {
    var isSignUp by remember { mutableStateOf(true) }
    var isLoggedIn by remember { mutableStateOf(false) }

    if (isLoggedIn) {
        MainPage {
            mAuth?.signOut()
            isLoggedIn = false
            isSignUp = true
        }
    } else {
        if (isSignUp) {
            SignUpScreen(mAuth) { isSignUp = false }
        } else {
            SignInScreen(mAuth, { isSignUp = true }) { isLoggedIn = true }
        }
    }
}
