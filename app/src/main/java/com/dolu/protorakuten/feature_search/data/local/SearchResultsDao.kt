package com.dolu.protorakuten.feature_search.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dolu.protorakuten.feature_search.data.local.entity.SearchResultsEntity
import com.dolu.protorakuten.feature_search.domain.model.SearchResults

@Dao
interface SearchResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResults(searchResult: SearchResultsEntity)

    @Query("DELETE FROM searchresultsentity WHERE title = :title")
    fun deleteSearchByTitle(title: String)

    @Query("SELECT * FROM searchresultsentity WHERE title = :query")
    fun getSearchResult(query: String) : SearchResultsEntity?
}