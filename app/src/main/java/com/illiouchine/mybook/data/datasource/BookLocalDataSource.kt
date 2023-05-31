package com.illiouchine.mybook.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.illiouchine.mybook.data.dataobject.BookDataObject
import com.illiouchine.mybook.data.dataobject.LastAuthorAndTitle
import com.illiouchine.mybook.data.dataobject.LikedBookDataObject

@Dao
interface BookLocalDataSource {

    @Insert
    suspend fun saveSearchResult(bookList: List<BookDataObject>)
    @Query("SELECT * FROM searched_book")
    suspend fun getLastSearchResult(): List<BookDataObject>
    @Query("DELETE FROM searched_book")
    suspend fun dropLastSearchResult()

    @Insert
    suspend fun saveLastAuthorAndTitle(authorAndTitle: LastAuthorAndTitle)
    @Query("DELETE FROM author_title")
    suspend fun dropLastAuthorAndTitle()

    @Query("SELECT * FROM author_title")
    suspend fun getLastAuthorAndTitle(): LastAuthorAndTitle?

    @Query("SELECT * FROM liked_book")
    suspend fun getLikedBooks(): List<LikedBookDataObject>

}