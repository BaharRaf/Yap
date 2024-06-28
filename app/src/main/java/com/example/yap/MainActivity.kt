package com.example.yap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.yap.authentication.AuthNavigation
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
}
