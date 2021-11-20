package com.dolu.protorakuten.feature_search.presentation.util

sealed class Screen(val route: String) {
    object SearchScreen: Screen("search_screen")
    object ProductDetailsScreen: Screen("product_details_screen")
}