package com.example.yap.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.yap.screens.MainPage
import com.example.yap.screens.SignInScreen
import com.example.yap.screens.SignUpScreen
import com.google.firebase.auth.FirebaseAuth

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
