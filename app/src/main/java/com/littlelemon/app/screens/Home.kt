package com.littlelemon.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.room.Room
import coil.compose.AsyncImage
import com.littlelemon.app.R
import com.littlelemon.app.components.HeroSection
import com.littlelemon.app.data.AppDatabase
import com.littlelemon.app.data.MenuItemEntity

@Composable
fun Home(navController: NavHostController) {
    val context = LocalContext.current
    val database = remember {
        Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }
    
    val menuItems by database.menuDao().getAll().observeAsState(emptyList())
    var searchPhrase by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    val filteredItems = menuItems.filter {
        it.title.contains(searchPhrase, ignoreCase = true) &&
        (selectedCategory.isEmpty() || it.category == selectedCategory)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // 1. Header with Profile Icon
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(50.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp, 40.dp)
            )
            IconButton(onClick = { navController.navigate("Profile") }) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier.size(40.dp).clip(RoundedCornerShape(20.dp))
                )
            }
        }

        // 2. Hero Section with Search
        HeroSection(searchPhrase) { searchPhrase = it }

        // 3. Menu Breakdown (Categories)
        Text(
            "ORDER FOR DELIVERY!",
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp
        )
        
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Starters", "Mains", "Desserts", "Drinks").forEach { category ->
                Button(
                    onClick = { selectedCategory = if (selectedCategory == category.lowercase()) "" else category.lowercase() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == category.lowercase()) Color(0xFF495E57) else Color(0xFFEDEFEE)
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(category, color = if (selectedCategory == category.lowercase()) Color.White else Color(0xFF495E57))
                }
            }
        }

        Divider(modifier = Modifier.padding(horizontal = 20.dp), thickness = 1.dp, color = Color.LightGray)

        // 4. Food Menu List
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
            items(filteredItems) { item ->
                MenuItem(item)
                Divider(thickness = 0.5.dp, color = Color.LightGray)
            }
        }
    }
}

@Composable
fun MenuItem(item: MenuItemEntity) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(item.description, color = Color.Gray, maxLines = 2, modifier = Modifier.padding(vertical = 5.dp))
            Text("$${item.price}", fontWeight = FontWeight.SemiBold)
        }
        AsyncImage(
            model = item.image,
            contentDescription = item.title,
            modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}