package com.dolu.protorakuten.core.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dolu.protorakuten.core.domain.model.ProductDetails
import com.dolu.protorakuten.core.domain.model.SaleType
import com.dolu.protorakuten.core.domain.model.SellerInfo
import com.dolu.protorakuten.core.util.Resource
import com.dolu.protorakuten.feature_search.domain.use_case.GetProductDetails
import com.dolu.protorakuten.feature_search.presentation.product.SalePage
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetails,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    lateinit var productDetailsObservable: Observable<ProductDetailsState>

    private val _eventFlow = PublishSubject.create<UIEvent>()
    val eventFlow = _eventFlow

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }

    private var _salePagesEmitter = PublishSubject.create<List<SalePage>>()
    val salePagesEmitter = _salePagesEmitter

    init {
        Log.i(TAG, "init VM products")
        savedStateHandle.get<String>("productId")?.let { productId ->
            productDetailsObservable = getProductDetailUseCase(productId.toLong())
                .map {
                    if (it.data != null) {
                        publishPageList(it.data)
                    }
                    when (it) {
                        is Resource.Success -> {
                            ProductDetailsState(it.data, false)
                        }
                        is Resource.Error -> {
                            Log.e(TAG, "error")
                            _eventFlow.onNext(
                                UIEvent.ShowSnackBar(it.message ?: "")
                            )
                            ProductDetailsState(it.data, false)
                        }
                        is Resource.Loading -> {
                            Log.i(TAG, "loading")
                            ProductDetailsState(it.data, true)
                        }
                    }
                }
                .subscribeOn(Schedulers.io())
        }
    }

    companion object {
        val TAG: String = ProductDetailsViewModel::class.java.simpleName
    }

    private fun publishPageList(productDetails: ProductDetails) {
        val sellerInfo = SellerInfo(
            productDetails.seller,
            productDetails.sellerComment,
            productDetails.salePrice
        )


        var newPage: SalePage.New? =
            if (productDetails.newBestPrice > 0)
                SalePage.New(productDetails.newBestPrice)
            else
                null
        var usedPage: SalePage.Used? =
            if (productDetails.usedBestPrice > 0)
                SalePage.Used(productDetails.usedBestPrice)
            else
                null

        val pages = mutableListOf<SalePage>()

        when (productDetails.type) {
            SaleType.NEW.name -> {
                if (newPage != null) {
                    newPage.sellerInfo = sellerInfo
                } else {
                    newPage = SalePage.New(
                        productDetails.newBestPrice,
                        SellerInfo(
                            productDetails.seller,
                            productDetails.sellerComment,
                            productDetails.salePrice
                        )
                    )
                }
            }
            SaleType.USED.name -> {
                if (usedPage != null) {
                    usedPage.sellerInfo = sellerInfo
                } else {
                    usedPage = SalePage.Used(
                        bestPrice = productDetails.usedBestPrice,
                        sellerInfo = SellerInfo(
                            productDetails.seller,
                            productDetails.sellerComment,
                            productDetails.salePrice
                        )
                    )
                }

            }
        }

        newPage?.let {
            pages.add(it)
        }

        usedPage?.let {
            pages.add(it)
        }

        _salePagesEmitter.onNext(pages)
    }
}