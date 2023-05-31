package com.illiouchine.mybook.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.illiouchine.mybook.data.dataobject.BookDataObject
import com.illiouchine.mybook.data.dataobject.LastAuthorAndTitle
import com.illiouchine.mybook.data.dataobject.LikedBookDataObject
import com.illiouchine.mybook.data.datasource.BookLocalDataSource

@Database(
    entities = [LastAuthorAndTitle::class, BookDataObject::class, LikedBookDataObject::class],
    version = 1,
    exportSchema = false,
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookLocalDataSource
}