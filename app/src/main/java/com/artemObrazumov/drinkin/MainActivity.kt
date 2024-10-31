package com.artemObrazumov.drinkin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.PRODUCTS
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.DrinksListScreen
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrinkinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DrinksListScreen(
                        drinks = PRODUCTS,
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}