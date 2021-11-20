package com.dolu.protorakuten.feature_search.presentation.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dolu.protorakuten.core.presentation.ProductDetailsViewModel
import com.dolu.protorakuten.feature_search.presentation.SearchViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.rx2.asFlow

@Composable
fun ProductDetailsScreen(
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.productDetailsState.value

    val scaffoldState = rememberScaffoldState()
    // A surface container using the 'background' color from the theme
    LaunchedEffect(key1 = true) {
        viewModel
            .eventFlow
            .subscribeOn(Schedulers.io())
            .asFlow()
            .collectLatest { event ->
                when(event) {
                    is SearchViewModel.UIEvent.ShowSnackBar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message
                        )
                        //Toast.makeText(context, "${event.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    Box() {
        LazyColumn(
            Modifier
                .fillMaxSize()
        ) {
            item {
                TopSection(title = state.productDetails?.title?: "")
            }
        }
        if(state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun TopSection(title: String) {
    Column {
        //TODO Caroussel image
        Text(text = title)
    }
}