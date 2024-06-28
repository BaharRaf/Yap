package com.example.yap.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.bluehouse.enablevolte.ui.theme.BlueDark
import dev.bluehouse.enablevolte.ui.theme.BlueLight
import dev.bluehouse.enablevolte.ui.theme.WhiteF7

@Composable
fun MainPage(onLogoutClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLight) // Set background color to BlueLight
    ) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueDark) // Set header background color to BlueDark
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Yap!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = WhiteF7, // Set text color to WhiteF7
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(end = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = onLogoutClicked,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = WhiteF7 // Set button text color to WhiteF7
                )
            ) {
                Text("Sign Out")
            }
        }
        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
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
    }
}
