package com.example.gowanandroid.model.room

import androidx.room.Embedded
import androidx.room.Relation
import com.example.gowanandroid.model.bean.Article
import com.example.gowanandroid.model.bean.Tag


data class ReadHistory(
    @Embedded
    var article: Article,
    @Relation(parentColumn = "id", entityColumn = "article_id")
    var tags: List<Tag>
)