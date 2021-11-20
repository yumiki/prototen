package com.dolu.protorakuten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dolu.protorakuten.feature_search.presentation.SearchViewModel
import com.dolu.protorakuten.feature_search.presentation.components.SearchResultList
import com.dolu.protorakuten.ui.theme.ProtoRakutenTheme
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.rx2.asFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ProtoRakutenTheme {
                val viewModel: SearchViewModel = hiltViewModel()
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
                                }
                            }
                        }
                }

                Scaffold(
                    scaffoldState = scaffoldState) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background) {
                        Box {
                            Column {
                                TextField(
                                    value = viewModel.query.value,
                                    onValueChange = viewModel::onSearch,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    placeholder = {
                                        Text(text = "Search...")
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .padding(15.dp)
                                                .size(24.dp)
                                        )
                                    }
                                )
                                Divider()
                                SearchResultList(products = state.searchResultProducts?: emptyList())
                            }
                            if(state.isLoading) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
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
    }
}