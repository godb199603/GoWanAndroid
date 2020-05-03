package  com.example.gowanandroid.ui.common

import com.example.gowanandroid.model.api.RetrofitClient


/**
 * Created by xiaojianjun on 2019-11-27.
 */
class CollectRepository {
    suspend fun collect(id: Int) = RetrofitClient.apiService.collect(id).apiData()
    suspend fun uncollect(id: Int) = RetrofitClient.apiService.uncollect(id).apiData()
}