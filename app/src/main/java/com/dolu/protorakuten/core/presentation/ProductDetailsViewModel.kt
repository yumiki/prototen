package com.dolu.protorakuten.core.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dolu.protorakuten.core.util.Resource
import com.dolu.protorakuten.feature_search.domain.use_case.GetProductDetails
import com.dolu.protorakuten.feature_search.presentation.SearchViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetails,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _productDetailsState = mutableStateOf(ProductDetailsState())
    val productDetailsState: State<ProductDetailsState> = _productDetailsState

    private val _eventFlow = PublishSubject.create<SearchViewModel.UIEvent>()
    val eventFlow = _eventFlow

    sealed class UIEvent {
        data class ShowSnackBar(val message:String): UIEvent()
    }

    init {
        savedStateHandle.get<String>("productId")?.let { productId ->
            getProductDetailUseCase(productId.toLong())
                .subscribe({ productDetailsResources ->
                            when(productDetailsResources) {
                                is Resource.Success -> {
                                    _productDetailsState.value = _productDetailsState.value.copy(
                                        isLoading = false,
                                        productDetails = productDetailsResources.data
                                    )
                                }
                                is Resource.Error -> {
                                    _productDetailsState.value = _productDetailsState.value.copy(
                                        productDetails = productDetailsResources.data,
                                        isLoading = false
                                    )
                                    _eventFlow.onNext(SearchViewModel.UIEvent.ShowSnackBar(productDetailsResources.message ?: ""))
                                }
                                is Resource.Loading -> {
                                    _productDetailsState.value = _productDetailsState.value.copy(
                                        isLoading = true,
                                        productDetails = productDetailsResources.data
                                    )
                                }
                            }
                        }, { error ->
                            Log.e(TAG,"Une erreur s'est produite: ${error.localizedMessage}")
                            _eventFlow.onNext(SearchViewModel.UIEvent.ShowSnackBar(error.message ?: ""))
                        })
        }
    }

    companion object {
        val TAG: String = ProductDetailsViewModel::class.java.simpleName
    }
}