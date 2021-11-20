package com.dolu.protorakuten.core.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.dolu.protorakuten.core.domain.model.GlobalRating
import com.dolu.protorakuten.core.domain.model.RakutenImage
import com.dolu.protorakuten.feature_search.data.util.JsonParser
import com.dolu.protorakuten.feature_search.domain.model.Buybox
import com.squareup.moshi.Types

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromGlobalRatingtoJson(json: String): GlobalRating? {
        return jsonParser.fromJson<GlobalRating>(json, GlobalRating::class.java)
    }

    @TypeConverter
    fun toGlobalRatingfromJson(rating: GlobalRating): String? {
        return jsonParser.toJson(rating, Buybox::class.java)
    }

    @TypeConverter
    fun fromRakutenImageListToJson(json: String): List<RakutenImage> {
        return jsonParser.fromJson(
            json,
            Types.newParameterizedType(List::class.java, RakutenImage::class.java)
        ) ?: emptyList()
    }

    @TypeConverter
    fun toRakutenImageListfromJson(images: List<RakutenImage>): String {
        return jsonParser.toJson(
            images,
            Types.newParameterizedType(List::class.java, RakutenImage::class.java)
        ) ?: "[]"
    }

    @TypeConverter
    fun fromStringListToJson(json: String): List<String> {
        return jsonParser.fromJson(
            json,
            Types.newParameterizedType(List::class.java, String::class.java)
        ) ?: emptyList()
    }

    @TypeConverter
    fun toStringListfromJson(imagesUrls: List<String>): String {
        return jsonParser.toJson(
            imagesUrls,
            Types.newParameterizedType(List::class.java, String::class.java)
        ) ?: "[]"
    }
}