package com.example.gowanandroid.model.room

import androidx.room.*
import com.example.gowanandroid.model.bean.Article
import com.example.gowanandroid.model.bean.Tag

@Dao
interface ReadHistoryDao {
    @Transaction
    @Insert(entity = Article::class)
    suspend fun insert(article: Article): Long

    @Transaction
    @Insert(entity = Tag::class)
    suspend fun insertArticleTag(tag: Tag): Long

    @Transaction
    @Query("SELECT * FROM article")
    suspend fun queryAll(): List<ReadHistory>

    @Transaction
    @Query("SELECT * FROM article WHERE id = (:id)")
    suspend fun queryArticle(id: Int): Article?

    @Transaction
    @Delete(entity = Article::class)
    suspend fun deleteArticle(article: Article)

    @Transaction
    @Update(entity = Article::class)
    suspend fun updateArticle(article: Article)

}