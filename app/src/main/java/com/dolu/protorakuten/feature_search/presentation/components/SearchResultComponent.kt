package com.dolu.protorakuten.feature_search.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dolu.protorakuten.R
import com.dolu.protorakuten.core.model.Product


@Composable
fun SearchResultList(products: List<Product>) {
    LazyColumn (
        modifier = Modifier.fillMaxSize()
    ){
        items(products.size) { index ->
            SearchResultItem(item = products[index])
        }
    }
}

@Composable
fun SearchResultItem(item: Product) {
    Row {
        Image(
            painter = rememberImagePainter(data = item.imagesUrls.first()),
            contentDescription = "Image of ${item.title}",
            modifier = Modifier.size(64.dp)
        )
        Column {
            Text(text = item.title)
            Text(text = "${item.nbReviews} reviews")
        }
    }
}