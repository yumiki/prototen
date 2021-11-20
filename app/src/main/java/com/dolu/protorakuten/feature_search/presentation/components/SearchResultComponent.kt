package com.dolu.protorakuten.feature_search.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dolu.protorakuten.core.model.Product
import com.dolu.protorakuten.core.model.dummyProduct
import com.dolu.protorakuten.ui.theme.ProtoRakutenTheme
import com.gowtham.ratingbar.RatingBar
import java.text.DecimalFormat


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchResultList(products: List<Product>, modifier: Modifier, onItemClicked: (item: Product) -> Unit = {}) {
    LazyColumn (
        modifier = modifier
            .fillMaxSize()
    ){
        items(products.size) { index ->
            SearchResultItem(item = products[index], modifier= modifier, onItemClicked = onItemClicked)
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
                    modifier= modifier,
                    text = item.title,
                    fontSize= 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(
                        value = item.reviewsAverageNote.toFloat(),
                        size=12.dp,
                        padding=1.dp,
                        onRatingChanged = {

                        },
                        onValueChange = {

                        }
                    )
                    Text(
                        text = "${item.nbReviews} reviews",
                        fontSize= 10.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                        //fontSize = 14.dp
                    )
                }
                Text(
                    text = "Neuf dès ${ df.format(item.newBestPrice)}",
                    fontSize= 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                if(item.usedBestPrice > 0) {
                    Text(text = "Occasion dès ${item.usedBestPrice}")
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