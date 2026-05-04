package com.littlelemon.app.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.littlelemon.app.navigation.Onboarding

@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    val firstName = sharedPreferences.getString("firstName", "") ?: ""
    val lastName = sharedPreferences.getString("lastName", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderSection()
        
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Personal information", fontSize = 18.sp, modifier = Modifier.padding(vertical = 20.dp))
            
            Text("First Name: $firstName", modifier = Modifier.padding(vertical = 5.dp))
            Text("Last Name: $lastName", modifier = Modifier.padding(vertical = 5.dp))
            Text("Email: $email", modifier = Modifier.padding(vertical = 5.dp))

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    sharedPreferences.edit().clear().apply()
                    navController.navigate(Onboarding.route)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14))
            ) {
                Text("Log out", color = Color.Black)
            }
        }
    }
}