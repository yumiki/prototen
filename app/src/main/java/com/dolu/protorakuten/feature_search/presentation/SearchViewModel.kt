package com.dolu.protorakuten.feature_search.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dolu.protorakuten.core.model.Product
import com.dolu.protorakuten.core.util.Resource
import com.dolu.protorakuten.feature_search.domain.use_case.GetSearchResultProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.internal.operators.flowable.FlowableCountSingle
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultProducts: GetSearchResultProducts
) : ViewModel() {

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    private val _state = mutableStateOf(SearchState())
    val state : State<SearchState> = _state

    private val _eventFlow = PublishSubject.create<UIEvent>()
    val eventFlow = _eventFlow

    sealed class UIEvent {
        data class ShowSnackBar(val message:String): UIEvent()
    }

    private var searchFlowableDiposable: Disposable? = null

    fun onSearch(query: String) {
        _query.value = query
        searchFlowableDiposable?.dispose()
        searchFlowableDiposable =
            Flowable.interval(500, TimeUnit.MILLISECONDS)
                .take(1)
                .flatMap {
                    getSearchResultProducts(query)
                }
                .subscribe({ result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                searchResultProducts = result.data,
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                searchResultProducts = result.data,
                                isLoading = false
                            )
                            _eventFlow.onNext(UIEvent.ShowSnackBar(result.message ?: ""))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                searchResultProducts = result.data,
                                isLoading = true
                            )
                        }
                    }
                }, {
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.onNext(UIEvent.ShowSnackBar(it.message ?: ""))
                    Log.e("SearchViewModel", "Il y a un soucis ${it.localizedMessage}")
                    it.printStackTrace()
                })
    }
}