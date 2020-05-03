package com.example.gowanandroid.model.api


import com.example.gowanandroid.model.bean.Article
import com.example.gowanandroid.model.bean.Pagination
import com.example.gowanandroid.model.bean.UserInfo
import retrofit2.http.*


/**
 * Created by xiaojianjun on 2019-09-18.
 */
interface ApiService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }


    @GET("/article/top/json")
    suspend fun getTopArticleList(): ApiResult<List<Article>>

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): ApiResult<Any?>

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollect(@Path("id") id: Int): ApiResult<Any?>
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResult<UserInfo>
}