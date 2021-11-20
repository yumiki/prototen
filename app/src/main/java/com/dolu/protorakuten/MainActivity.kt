package com.dolu.protorakuten

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
            val context = LocalContext.current
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
                                    /*scaffoldState.snackbarHostState.showSnackbar(
                                        message = event.message
                                    )*/
                                    Toast.makeText(context, "${event.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                }

                Scaffold(
                    scaffoldState = scaffoldState) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background) {
                        Box () {
                            Column {
                                val focusManager = LocalFocusManager.current
                                TextField(
                                    value = viewModel.query.value,
                                    onValueChange = viewModel::onSearch,
                                    singleLine = true,
                                    modifier = Modifier
                                        .zIndex(1f)
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .background(MaterialTheme.colors.background),
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
                                    },
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            focusManager.clearFocus()
                                        })
                                )
                                Divider()
                                SearchResultList(products = state.searchResultProducts?: emptyList(), Modifier.zIndex(-5f))
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
        /*Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xA0A0A010))
                .blur(10.dp)) {
            BlurEffect(30f,30f)
        }*/
        Surface(
            color = Color.Black.copy(0.5f)
        ) {

        }
    }
}