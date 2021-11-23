package com.dolu.protorakuten.feature_search.presentation.search

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dolu.protorakuten.core.presentation.SearchField
import com.dolu.protorakuten.feature_search.presentation.SearchViewModel
import com.dolu.protorakuten.feature_search.presentation.components.SearchResultList
import com.dolu.protorakuten.feature_search.presentation.util.Screen
import com.dolu.protorakuten.ui.theme.ProtoRakutenTheme
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.rx2.asFlow

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
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

    Scaffold(
        scaffoldState = scaffoldState) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Box {
                Column {
                    LocalFocusManager.current
                    SearchField(
                        query = viewModel.query.value,
                        onValueChange = viewModel::onSearch
                    )
                    Divider()
                    AnimatedVisibility(
                        visible = state.searchResultProducts?.isNotEmpty() ?: false,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        SearchResultList(
                            products = state.searchResultProducts ?: emptyList(),
                            Modifier.zIndex(-5f),
                            onItemClicked = {
                                navController
                                    .navigate(
                                        Screen.ProductDetailsScreen.route + "?productId=${it.id}",
                                    )
                            })
                    }

                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    ProtoRakutenTheme {
    }
}


@Preview
@Composable
fun SearchFieldPreview() {
    ProtoRakutenTheme {
        SearchField(
            query = "Samsung",
            onValueChange = {}
        )
    }
}