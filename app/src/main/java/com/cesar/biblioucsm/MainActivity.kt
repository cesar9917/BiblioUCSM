package com.cesar.biblioucsm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.cesar.biblioucsm.data.network.RetrofitClient
import com.cesar.biblioucsm.data.repository.BookRepository
import com.cesar.biblioucsm.data.repository.UserRepository
import com.cesar.biblioucsm.navigation.NavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = RetrofitClient.apiService
        val userRepository = UserRepository(apiService)
        val bookRepository = BookRepository(apiService)

        setContent {
            val navController = rememberNavController()

            NavGraph(
                navController = navController,
                userRepository = userRepository,
                bookRepository = bookRepository
            )
        }
    }
}