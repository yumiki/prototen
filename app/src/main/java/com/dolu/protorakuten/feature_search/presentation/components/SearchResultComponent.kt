package com.dolu.protorakuten.feature_search.presentation.components

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dolu.protorakuten.R
import com.dolu.protorakuten.core.domain.model.Product
import com.dolu.protorakuten.core.domain.model.dummyProduct
import com.dolu.protorakuten.core.presentation.PriceTextField
import com.dolu.protorakuten.core.presentation.ReviewBar
import com.dolu.protorakuten.ui.theme.ProtoRakutenTheme
import java.text.DecimalFormat


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchResultList(products: List<Product>, modifier: Modifier, onItemClicked: (item: Product) -> Unit = {}) {
    LazyColumn (
        modifier = modifier
            .fillMaxSize()
    ){
        items(products.size) { index ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SearchResultItem(item = products[index], modifier= modifier, onItemClicked = onItemClicked)
            }
            if (index < products.size - 1) {
                Divider()
            }
        }
    }
}

@Composable
fun SearchResultItem(item: Product, modifier: Modifier = Modifier, onItemClicked: (item: Product) -> Unit = {}) {
    Box(modifier
        .focusable(true)
        .clickable {
            onItemClicked(item)
        }
        .padding(16.dp)
        .fillMaxWidth()
    ) {
        Row(modifier) {
            Image(
                painter = rememberImagePainter(data = item.imagesUrls.first()),
                contentDescription = "Image of ${item.title}",
                modifier = Modifier
                    .size(64.dp)
                    .align(CenterVertically)
            )
            Column(
                modifier.padding(start = 16.dp)
            ) {
                val df = DecimalFormat.getCurrencyInstance()
                df.maximumFractionDigits = 2
                df.minimumFractionDigits = 0
                Text(
                    modifier = modifier,
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                ReviewBar(
                    review = item.review,
                    starSize = 12.dp,
                    fontSize = 10.sp
                )
                PriceTextField(
                    price = item.newBestPrice,
                    new = true,
                    prefix = stringResource(R.string.new_product_price_label)
                )
                if (item.usedBestPrice > 0) {
                    PriceTextField(
                        price = item.usedBestPrice,
                        new = false,
                        prefix = stringResource(R.string.used_product_price_label)
                    )
                }
            }
        }
    }

}


@Preview
@Composable
fun SearchResultItemPreview() {
    ProtoRakutenTheme {
        SearchResultItem(item = dummyProduct)
    }
}