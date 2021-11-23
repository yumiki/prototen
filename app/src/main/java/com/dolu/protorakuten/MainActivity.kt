package com.dolu.protorakuten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dolu.protorakuten.feature_search.presentation.product.ProductDetailsScreen
import com.dolu.protorakuten.feature_search.presentation.search.SearchScreen
import com.dolu.protorakuten.feature_search.presentation.util.Screen
import com.dolu.protorakuten.ui.theme.ProtoRakutenTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProtoRakutenTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SearchScreen.route
                    ) {
                        composable(route = Screen.SearchScreen.route) {
                            SearchScreen(navController = navController)
                        }
                        composable(
                            route = Screen.ProductDetailsScreen.route + "?productId={productId}",
                            arguments = listOf(navArgument(name = "productId") {
                                type = NavType.StringType
                                defaultValue = ""
                            })
                        ) {
                            ProductDetailsScreen(
                                navController = navController,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProtoRakutenTheme {
        Greeting("Android")
        Surface(
            color = Color.Black.copy(0.5f)
        ) {

        }
    }
}