package com.littlelemon.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.littlelemon.app.R

@Composable
fun HeroSection(searchPhrase: String, onSearchChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .background(Color(0xFF495E57))
            .padding(20.dp)
    ) {
        Text("Little Lemon", fontSize = 40.sp, color = Color(0xFFF4CE14))
        
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Chicago", fontSize = 24.sp, color = Color.White)
                Text(
                    "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    color = Color.White,
                    modifier = Modifier.padding(top = 10.dp),
                    lineHeight = 20.sp
                )
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                modifier = Modifier.size(130.dp).clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        OutlinedTextField(
            value = searchPhrase,
            onValueChange = onSearchChange,
            placeholder = { Text("Enter search phrase") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth().background(Color(0xFFEDEFEE), RoundedCornerShape(8.dp)),
            singleLine = true
        )
    }
}