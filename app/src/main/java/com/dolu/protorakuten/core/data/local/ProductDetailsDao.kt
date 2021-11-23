package com.dolu.protorakuten.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dolu.protorakuten.core.data.local.entity.ProductDetailsEntity

@Dao
interface ProductDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductDetails(product: ProductDetailsEntity)

    @Query("DELETE FROM productdetailsentity WHERE id = :productId")
    fun deleteProductDetailsById(productId: Long)

    @Query("SELECT * FROM productdetailsentity WHERE id = :productId")
    fun getProductDetailsById(productId: Long): ProductDetailsEntity?

    @Query("SELECT * FROM productdetailsentity")
    fun getAllProductDetails(): List<ProductDetailsEntity>
}