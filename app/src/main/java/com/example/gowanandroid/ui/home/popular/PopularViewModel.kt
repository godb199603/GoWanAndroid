package com.example.gowanandroid.ui.home.popular

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gowanandroid.model.LoadMoreStatus
import com.example.gowanandroid.model.api.RetrofitClient
import com.example.gowanandroid.ui.base.BaseViewModel

import com.example.gowanandroid.model.bean.Article
import com.example.gowanandroid.ui.common.CollectRepository
import com.example.gowanandroid.util.bus.Bus
import com.example.gowanandroid.util.bus.USER_COLLECT_UPDATED
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * 作者：Create on 2020/4/22 17:30  by  wzl
 * 描述：
 * 最近修改：2020/4/22 17:30 modify by wzl
 */
class PopularViewModel : BaseViewModel() {


    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()

    companion object {
        const val INITIAL_PAGE = 0
    }
    private var page = INITIAL_PAGE

    private val popularRepository by lazy { PopularRepository() }
    private val collectRepository by lazy { CollectRepository() }

    val articleList: MutableLiveData<MutableList<Article>> = MutableLiveData()

    fun refreshArticleList() {
        refreshStatus.value = true
        reloadStatus.value = false
        launch(
            block = {
                val topArticleListDefferd = async {
                    popularRepository.getTopArticleList()
                }
                val paginationDefferd = async {
                    popularRepository.getArticleList(INITIAL_PAGE)
                }
                val topArticleList = topArticleListDefferd.await()
                    .apply { forEach { it.top = true } }
                val pagination = paginationDefferd.await()
                page = pagination.curPage
                articleList.value = mutableListOf<Article>().apply {
                    addAll(topArticleList)
                    addAll(pagination.datas)
                }
                refreshStatus.value = false
            },
            error = {
                refreshStatus.value = false
                reloadStatus.value = page == INITIAL_PAGE
            }
        )
    }

    fun loadMoreArticleList() {
        loadMoreStatus.value = LoadMoreStatus.LOADING
        launch(
            block = {
                val pagination = popularRepository.getArticleList(page)
                page = pagination.curPage
                val currentList = articleList.value ?: mutableListOf()
                currentList.addAll(pagination.datas)
                articleList.value = currentList
                loadMoreStatus.value = if (pagination.offset >= pagination.total) {
                    LoadMoreStatus.END
                } else {
                    LoadMoreStatus.COMPLETED
                }
            },
            error = {
                loadMoreStatus.value = LoadMoreStatus.ERROR
            }
        )
    }


    fun collect(id: Int) {
        launch(
            block = {
                collectRepository.collect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (!collectIds.contains(id)) collectIds.add(id)
                })
                updateItemCollectState(id to true)
                Bus.post(USER_COLLECT_UPDATED, id to true)
            },
            error = {
                updateItemCollectState(id to false)
            }
        )
    }

    fun uncollect(id: Int) {
        launch(
            block = {
                collectRepository.uncollect(id)
                userRepository.updateUserInfo(userRepository.getUserInfo()!!.apply {
                    if (collectIds.contains(id)) collectIds.remove(id)
                })
                updateItemCollectState(id to false)
                Bus.post(USER_COLLECT_UPDATED, id to false)
            },
            error = {
                updateItemCollectState(id to true)
            }
        )
    }
    /**
     * 更新列表收藏状态
     */
    fun updateListCollectState() {
        val list = articleList.value
        if (list.isNullOrEmpty()) return
        if (userRepository.isLogin()) {
            val collectIds = userRepository.getUserInfo()?.collectIds ?: return
            list.forEach { it.collect = collectIds.contains(it.id) }
        } else {
            list.forEach { it.collect = false }
        }
        articleList.value = list
    }

    /**
     * 更新Item的收藏状态
     */
    fun updateItemCollectState(target: Pair<Int, Boolean>) {
        val list = articleList.value
        val item = list?.find { it.id == target.first } ?: return
        item.collect = target.second
        articleList.value = list
    }




//    fun refreshArticleList() {
//        viewModelScope.launch {
//            val topArticleListDefferd =
//                viewModelScope.async { RetrofitClient.apiService.getTopArticleList().apiData() }
////                async {
////                    RetrofitClient.apiService.getTopArticleList().apiData()
////                }
//
//            val paginationDefferd =
//                viewModelScope.async { RetrofitClient.apiService.getArticleList(page).apiData() }
////                    async {
////                    RetrofitClient.apiService.getArticleList(page).apiData()
////                }
//            val topArticleList = topArticleListDefferd.await()
//                .apply { forEach { it.top = true } }
//            val pagination = paginationDefferd.await()
//            page = pagination.curPage
//            articleList.value = mutableListOf<Article>().apply {
//                addAll(topArticleList)
//                addAll(pagination.datas)
//            }
//
//        }
//    }

}






