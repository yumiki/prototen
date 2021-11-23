package com.dolu.protorakuten.feature_search.presentation.product

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.dolu.protorakuten.R
import com.dolu.protorakuten.core.domain.model.*
import com.dolu.protorakuten.core.presentation.*
import com.dolu.protorakuten.ui.theme.Grey600
import com.dolu.protorakuten.ui.theme.ProtoRakutenTheme
import com.google.accompanist.pager.*
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.asFlow
import java.lang.Float.min

const val TAG = "ProductDetailsScreen"

@ExperimentalPagerApi
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val state = viewModel
        .productDetailsObservable
        .subscribeAsState(initial = ProductDetailsState())
        .value

    val saleInfoState = viewModel
        .salePagesEmitter
        .subscribeAsState(initial = emptyList())
        .value

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel
            .eventFlow
            .subscribeOn(Schedulers.io())
            .asFlow()
            .collectLatest { event ->
                when (event) {
                    is ProductDetailsViewModel.UIEvent.ShowSnackBar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }
                }
            }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DetailTopAppBar(onBack = {
                navController.navigateUp()
            })
        }
    ) {
        val lazyListState = rememberLazyListState()
        Box {
            LazyColumn(
                Modifier
                    .fillMaxSize(),
                state = lazyListState
            ) {
                item {
                    TopSection(
                        title = state.productDetails?.title ?: "",
                        images = state.productDetails?.images ?: emptyList(),
                        review = state.productDetails?.globalRating,
                        isLoading = state.isLoading,
                        categories = state.productDetails?.categories ?: emptyList(),
                        modifier = Modifier.graphicsLayer {
                            alpha = min(1f, 1 - (lazyListState.firstVisibleItemScrollOffset / 600f))
                            translationY = -lazyListState.firstVisibleItemScrollOffset * 0.1f
                        }
                    )

                    ProductDetailSpacer()

                    state.productDetails?.let {
                        if (saleInfoState.isNotEmpty()) {
                            SaleSection(saleInfoState)
                            ProductDetailSpacer()
                        }

                        Description(
                            it.description
                        )
                    }
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}

sealed class SalePage(
    val saleType: SaleType,
    var sellerInfo: SellerInfo? = null,
    val bestPrice: Double
) {
    class New(bestPrice: Double, sellerInfo: SellerInfo? = null) :
        SalePage(SaleType.NEW, sellerInfo, bestPrice)

    class Used(
        bestPrice: Double,
        val quality: Quality = Quality.UNKNOWN,
        sellerInfo: SellerInfo? = null
    ) : SalePage(SaleType.USED, sellerInfo, bestPrice) {
        enum class Quality {
            UNKNOWN,
            NEW;

            fun getResourceStringId(): Int {
                TODO("Need to be implemented")
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun SaleSection(
    salePages: List<SalePage>
) {
    val pagerState = rememberPagerState()

    PagerSwitch(pagerState, salePages)

    ProductDetailSpacer()

    HorizontalPager(
        count = salePages.size,
        state = pagerState,
    ) { index ->
        val currentPage = salePages[index]
        Column(
            Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {
            val priceToDisplay = currentPage.sellerInfo?.salePrice ?: currentPage.bestPrice

            PriceTextField(
                price = priceToDisplay,
                new = currentPage.saleType == SaleType.NEW
            )
            when (currentPage.saleType) {
                SaleType.NEW -> {
                    Text(
                        text = stringResource(id = R.string.new_product_details_page_price_label),
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.primary
                    )
                }
                SaleType.USED -> {
                    Row {
                        Text(
                            text = stringResource(id = R.string.used_product_details_page_price_label),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        if ((currentPage as SalePage.Used).quality != SalePage.Used.Quality.UNKNOWN) {
                            Text(
                                text = stringResource(id = currentPage.quality.getResourceStringId()),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                SaleType.REFURB -> TODO()
            }

            Button(
                onClick = { /*TODO*/ },
                Modifier
                    .fillMaxWidth()
                    .requiredHeightIn(40.dp, 60.dp)
            ) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = stringResource(id = R.string.add_to_cart_button),
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = stringResource(id = R.string.add_to_cart_button),
                )
            }

            currentPage.sellerInfo?.let {
                Text(
                    text = it.name,
                    fontWeight = FontWeight.Bold
                )
                Text(text = it.comment)
            }

            Button(
                onClick = { /*TODO*/ },
                Modifier
                    .fillMaxWidth()
                    .requiredHeightIn(40.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = MaterialTheme.colors.onSecondary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.contact_seller_button),
                )
            }
        }
    }

}

@ExperimentalPagerApi
@Composable
fun PagerSwitch(pagerState: PagerState, salePages: List<SalePage>) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        val coroutineScope = rememberCoroutineScope()

        val pageMap = salePages.associateBy(
            { it.saleType },
            { it }
        )

        val modifier = Modifier.padding(horizontal = 5.dp)

        fun onClick(saleType: SaleType) {
            coroutineScope.launch {
                pagerState
                    .animateScrollToPage(
                        salePages.indexOf(pageMap[saleType])
                    )
            }
        }

        PagerSwitchButton(
            modifier = modifier,
            saletype = SaleType.NEW,
            enabled = pageMap.containsKey(SaleType.NEW),
            onClick = ::onClick
        )
        PagerSwitchButton(
            modifier = modifier,
            saletype = SaleType.USED,
            enabled = pageMap.containsKey(SaleType.USED),
            onClick = ::onClick
        )
        PagerSwitchButton(
            modifier = modifier.weight(2f),
            saletype = SaleType.REFURB,
            enabled = pageMap.containsKey(SaleType.REFURB),
            onClick = ::onClick,
        )
    }
}

@Composable
fun PagerSwitchButton(
    modifier: Modifier,
    saletype: SaleType,
    onClick: (saleType: SaleType) -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = { onClick(saletype) },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
        ),
        enabled = enabled
    ) {
        val title = when (saletype) {
            SaleType.NEW -> stringResource(id = R.string.new_button_tab_label)
            SaleType.USED -> stringResource(id = R.string.used_button_tab_label)
            SaleType.REFURB -> stringResource(id = R.string.refurb_button_tab_label)
        }
        Text(
            text = title,
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun SaleSectionPreview() {
    ProtoRakutenTheme {
        Surface(color = MaterialTheme.colors.background) {
            SaleSection(salePages = listOf(SalePage.Used(60.0)))
        }
    }
}


@ExperimentalPagerApi
@Composable
fun Description(description: String) {
    val pagerState = rememberPagerState()

    ScrollableTabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onSecondary,
        edgePadding = 0.dp
    ) {
        Tab(
            text = {
                Text(
                    stringResource(id = R.string.description_pager_tab_label),
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )
            },
            selected = true,
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        )
    }

    HorizontalPager(
        count = 1,
        state = pagerState,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Html(text = description)
    }
}

@ExperimentalPagerApi
@Preview
@Composable
fun PreviewDescription() {
    ProtoRakutenTheme {
        Surface(color = MaterialTheme.colors.background) {
            Description("Quick description")
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TopSection(
    title: String,
    images: List<RakutenImage>,
    isLoading: Boolean,
    review: GlobalRating?,
    categories: List<String>,
    modifier: Modifier
) {
    val pagerState = rememberPagerState()
    Column {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = modifier
        ) { index ->
            val image = images[index].links[Size.ORIGINAL]
            Log.i(TAG, "Image: $image")
            Image(
                painter = rememberImagePainter(data = image),
                contentDescription = "Slide $index",
                modifier = Modifier
                    .size(250.dp)
            )
        }
        if (!isLoading && pagerState.pageCount > 0) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Column(Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )

            Text(
                text = categories.joinToString("-"),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = MaterialTheme.colors.primaryVariant
            )

            review?.let {
                ReviewBar(
                    review = review,
                    starSize = 16.dp,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun DetailTopAppBar(onBack: () -> Unit = {}) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_action_cd),
                    //tint = Color.Black
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface
    )
}

@Composable
fun ProductDetailSpacer() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .size(8.dp)
            .background(Grey600)
    )
}


@Preview
@Composable
fun DetailTopAppBarPreview() {
    ProtoRakutenTheme(true) {
        DetailTopAppBar()
    }
}