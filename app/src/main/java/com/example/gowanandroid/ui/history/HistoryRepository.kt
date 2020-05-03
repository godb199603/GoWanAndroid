package com.example.gowanandroid.ui.history

import com.example.gowanandroid.model.bean.Article
import com.example.gowanandroid.room.RoomHelper


/**
 * Created by xiaojianjun on 2019-11-28.
 */
class HistoryRepository {

    suspend fun getReadHistory() = RoomHelper.queryAllReadHistory()
    suspend fun deleteHistory(article: Article) = RoomHelper.deleteReadHistory(article)

}