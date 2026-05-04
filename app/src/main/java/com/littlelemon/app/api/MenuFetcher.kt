package com.littlelemon.app.api

import com.littlelemon.app.data.AppDatabase
import com.littlelemon.app.data.MenuItemEntity
import com.littlelemon.app.data.MenuItemNetwork
import com.littlelemon.app.data.MenuNetwork
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class MenuFetcher(private val database: AppDatabase) {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun fetchMenu() {
        if (database.menuDao().isEmpty()) {
            val response: MenuNetwork = httpClient
                .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                .body()
            
            val menuItems = response.menu.map { it.toEntity() }
            database.menuDao().insertAll(*menuItems.toTypedArray())
        }
    }

    private fun MenuItemNetwork.toEntity() = MenuItemEntity(
        id = id,
        title = title,
        description = description,
        price = price,
        image = image,
        category = category
    )
}