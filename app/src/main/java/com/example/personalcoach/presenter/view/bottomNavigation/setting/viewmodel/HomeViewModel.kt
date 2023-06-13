package com.example.personalcoach.presenter.view.bottomNavigation.setting.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalcoach.domain.model.SlideScreen
import com.example.personalcoach.domain.model.item.ItemResponse
import com.example.personalcoach.data.network.RestClient
import com.example.personalcoach.data.network.repository.ItemRepository
import com.example.personalcoach.domain.utils.ApiState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) :ViewModel() {

    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)
    init {
        fetchItems()
    }

    fun fetchItems(name: String = "",brandName: String = "",typeName: String = "") {
        viewModelScope.launch {
            itemRepository.getItems().onStart {
                response.value=ApiState.Loading
            }.catch {
                response.value = ApiState.Failure(it)
            }.collect{
                response.value = ApiState.Success(it)
            }
        }
    }
    private val _search = MutableLiveData<String>()
    val searchRequest: LiveData<String>
        get()=_search

    fun getRequest(request: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _search.value = request
                println(_search.value)
            }
        }
    }

}

enum class Side{
    Left, Right, Center
}

enum class Vertical{
    Top, Bottom, Center
}

data class TrendingCourse(
    val imageUrl: Int,
    val name: String,
    val type: String,
    val rating: Float,
    val programTime: String
)

