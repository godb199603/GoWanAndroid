package com.example.gowanandroid.room

import androidx.room.Room
import com.example.gowanandroid.App
import com.example.gowanandroid.model.bean.Article
import com.example.gowanandroid.model.bean.Tag
import com.example.gowanandroid.model.room.AppDatabase


object RoomHelper {

    private val appDatabase by lazy {
        Room.databaseBuilder(App.instance, AppDatabase::class.java, "database_wanandroid").build()
    }

    private val readHistoryDao by lazy { appDatabase.readHistoryDao() }

    suspend fun queryAllReadHistory() = readHistoryDao.queryAll()
        .map { it.article.apply { tags = it.tags } }.reversed()

    suspend fun addReadHistory(article: Article) {
        readHistoryDao.queryArticle(article.id)?.let {
            readHistoryDao.deleteArticle(it)
        }
        readHistoryDao.insert(article.apply { primaryKeyId = 0 })
        article.tags.forEach {
            readHistoryDao.insertArticleTag(
                Tag(id = 0, articleId = article.id.toLong(), name = it.name, url = it.url)
            )
        }
    }

    suspend fun deleteReadHistory(article: Article) = readHistoryDao.deleteArticle(article)
}