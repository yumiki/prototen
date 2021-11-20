package com.dolu.protorakuten.feature_search.data.local

import androidx.room.Dao

@Dao
interface ProductDao {

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<ProductEntity>)*/

    /*@Query("DELETE FROM productentity WHERE id in(:productIds)")
    fun deleteProductsById(productIds: List<Long>)*/

    /*@Query("SELECT * FROM productentity")
    fun getAllProducts(): List<ProductEntity>*/
}
