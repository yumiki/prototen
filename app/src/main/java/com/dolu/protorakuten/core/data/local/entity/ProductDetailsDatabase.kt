package com.dolu.protorakuten.core.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dolu.protorakuten.core.data.local.Converters
import com.dolu.protorakuten.core.data.local.ProductDetailsDao

@Database(
    entities = [ProductDetailsEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ProductDetailsDatabase: RoomDatabase() {
    abstract val dao: ProductDetailsDao
}