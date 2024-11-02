package com.artemObrazumov.drinkin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.artemObrazumov.drinkin.dashboard.presentation.product_details.ProductDetailsScreen
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.PRODUCTS
import com.artemObrazumov.drinkin.dashboard.presentation.products_list.ProductsListScreen
import com.artemObrazumov.drinkin.ui.theme.DrinkinTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrinkinTheme {
                enableEdgeToEdge()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var showDetails by remember {
                        mutableStateOf(false)
                    }
                    var a by remember {
                        mutableIntStateOf(0)
                    }
                    SharedTransitionLayout {
                        AnimatedContent(
                            showDetails,
                            label = "basic_transition",
                            modifier = Modifier
                                .padding(innerPadding)
                        ) { targetState ->
                            if (!targetState) {
                                ProductsListScreen(
                                    products = PRODUCTS,
                                    onProductClick = {
                                        showDetails = true
                                        a = it.id
                                    },
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                            } else {
                                ProductDetailsScreen(
                                    a = a,
                                    animatedVisibilityScope = this@AnimatedContent
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}