package com.example.yap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.yap.authentication.AuthNavigation
import com.example.yap.ui.theme.YapTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase App
        FirebaseApp.initializeApp(this)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Ensure Firebase Realtime Database uses secure HTTPS URL
        val database = FirebaseDatabase.getInstance("https://yapmessenger-647f4-default-rtdb.europe-west1.firebasedatabase.app/")
        database.setPersistenceEnabled(true)  // Enable offline capabilities

        setContent {
            YapTheme {
                AuthNavigation(mAuth)
            }
        }
    }
}
