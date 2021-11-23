package com.dolu.protorakuten.core.presentation

import android.widget.TextView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.text.HtmlCompat
import com.dolu.protorakuten.R
import com.dolu.protorakuten.core.domain.model.GlobalRating
import com.dolu.protorakuten.ui.theme.ProtoRakutenTheme
import com.dolu.protorakuten.ui.theme.Red500
import com.gowtham.ratingbar.RatingBar
import java.text.DecimalFormat

@Composable
fun ReviewBar(
    review: GlobalRating,
    starSize: Dp,
    fontSize: TextUnit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RatingBar(
            value = review.score.toFloat(),
            size = starSize,
            padding = 0.dp,
            onRatingChanged = {

            },
            onValueChange = {

            }
        )
        Text(
            text = stringResource(id = R.string.review_label, review.nbReviews),
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Composable
fun Html(text: String) {
    AndroidView(factory = { context ->
        TextView(context).apply {
            val htmlFlags =
                HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS or
                        HtmlCompat.FROM_HTML_MODE_COMPACT
            setText(
                HtmlCompat.fromHtml(
                    text,
                    htmlFlags
                )
            )
            setTextColor(resources.getColor(R.color.black, null))
        }
    })
}

@Composable
fun SearchField(
    query: String,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Surface(elevation = 5.dp) {
        TextField(
            value = query,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    BorderStroke(1.dp, Color.Gray.copy(0.2f)),
                    shape = MaterialTheme.shapes.small
                )
                .shadow(
                    1.dp,
                    MaterialTheme.shapes.small
                ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(text = stringResource(R.string.search_hint))
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
    }
}

/**
 * New determine if is new or used
 */
@Composable
fun PriceTextField(
    price: Double,
    new: Boolean,
    pricefontSize: TextUnit = 16.sp,
    prefixfontSize: TextUnit = 12.sp,
    prefix: String? = null
) {

    val color = if (new) Red500 else MaterialTheme.colors.onSecondary
    val df = DecimalFormat.getCurrencyInstance()
    //df.maximumFractionDigits = 2
    //df.minimumFractionDigits = 0

    val newPrefixSize = prefixfontSize.times(1.2)
    val newPriceSize = pricefontSize.times(1.2)

    Row {
        prefix?.let {
            Text(
                text = prefix,
                color = color,
                fontSize = if (new) newPrefixSize else prefixfontSize,
                //fontWeight = FontWeight.Light,
                modifier = Modifier.alignByBaseline()
            )
        }
        Text(
            text = df.format(price),
            fontSize = if (new) newPriceSize else pricefontSize,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.alignByBaseline()
        )
    }

}

@Preview
@Composable
fun PreviewPriceTag() {
    ProtoRakutenTheme {
        PriceTextField(price = 250.0, new = true, prefix = "Neuf dès ")
    }
    ProtoRakutenTheme {
        PriceTextField(price = 250.0, new = false, prefix = "Occassion dès ")
    }
}