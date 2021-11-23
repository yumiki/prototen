package com.dolu.protorakuten.core.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.dolu.protorakuten.core.domain.model.GlobalRating
import com.dolu.protorakuten.core.domain.model.RakutenImage
import com.dolu.protorakuten.core.domain.model.Size
import com.dolu.protorakuten.feature_search.data.util.JsonParser
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
        return jsonParser.toJson(rating, GlobalRating::class.java)
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
    fun fromMapSizeStringToJson(json: String): Map<Size, String> {
        return jsonParser.fromJson(
            json,
            Types.newParameterizedType(Map::class.java, Size::class.java, String::class.java)
        ) ?: mapOf()
    }

    @TypeConverter
    fun toMapSizeStringfromJson(images: Map<Size, String>): String {
        return jsonParser.toJson(
            images,
            Types.newParameterizedType(Map::class.java, Size::class.java, String::class.java)
        ) ?: "{}"
    }


    @TypeConverter
    fun toSizefromJson(json: String): Size? {
        return jsonParser.fromJson<Size>(json, Size::class.java)
    }

    @TypeConverter
    fun fromSizetoJson(size: Size): String? {
        return jsonParser.toJson(size, Size::class.java)
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