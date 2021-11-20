package com.dolu.protorakuten.feature_search.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dolu.protorakuten.feature_search.data.local.Converters
import com.dolu.protorakuten.feature_search.data.local.ProductDao


@Database(
    entities = [ProductEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ProductDatabase: RoomDatabase() {
    abstract val dao: ProductDao
}