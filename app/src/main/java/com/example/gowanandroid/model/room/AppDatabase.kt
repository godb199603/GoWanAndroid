package com.example.gowanandroid.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gowanandroid.model.bean.Article
import com.example.gowanandroid.model.bean.Tag

/**
 * Created by xiaojianjun on 2019-12-05.
 */
@Database(entities = [Article::class, Tag::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun readHistoryDao(): ReadHistoryDao
}