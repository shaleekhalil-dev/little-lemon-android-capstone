package com.littlelemon.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.littlelemon.app.api.MenuFetcher
import com.littlelemon.app.data.AppDatabase
import com.littlelemon.app.navigation.Navigation
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val sharedPreferences = getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        val menuFetcher = MenuFetcher(database)
        lifecycleScope.launch(Dispatchers.IO) {
            menuFetcher.fetchMenu()
        }

        setContent {
            val navController = rememberNavController()
            Navigation(navController = navController, isLoggedIn = isLoggedIn)
        }
    }
}