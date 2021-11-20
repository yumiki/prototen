package com.dolu.protorakuten.feature_search.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.dolu.protorakuten.core.domain.model.Product
import com.dolu.protorakuten.feature_search.data.util.JsonParser
import com.dolu.protorakuten.feature_search.domain.model.Buybox
import com.squareup.moshi.Types

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromBuyboxtoJson(json: String): Buybox? {
        return jsonParser.fromJson<Buybox>(json, Buybox::class.java)
    }

    @TypeConverter
    fun toBuyboxfromJson(buybox: Buybox): String? {
        return jsonParser.toJson(buybox, Buybox::class.java)
    }

    @TypeConverter
    fun fromProductstoJson(json: String): List<Product> {
        return jsonParser.fromJson(
            json,
            Types.newParameterizedType(List::class.java, Product::class.java)
        ) ?: emptyList()
    }

    @TypeConverter
    fun toProductsfromJson(products: List<Product>): String {
        return jsonParser.toJson(
            products,
            Types.newParameterizedType(List::class.java, Product::class.java)
        ) ?: "[]"
    }

    @TypeConverter
    fun fromImageListtoJson(json: String): List<String> {
        return jsonParser.fromJson(
            json,
            Types.newParameterizedType(List::class.java, String::class.java)
        ) ?: emptyList()
    }

    @TypeConverter
    fun toImageListfromJson(imagesUrls: List<String>): String {
        return jsonParser.toJson(
            imagesUrls,
            Types.newParameterizedType(List::class.java, String::class.java)
        ) ?: "[]"
    }
}