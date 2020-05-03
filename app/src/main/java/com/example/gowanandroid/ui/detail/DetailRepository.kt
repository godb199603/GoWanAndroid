package com.example.gowanandroid.ui.detail

import com.example.gowanandroid.model.bean.Article
import com.example.gowanandroid.room.RoomHelper


class DetailRepository {
    suspend fun saveReadHistory(article: Article) = RoomHelper.addReadHistory(article)
}