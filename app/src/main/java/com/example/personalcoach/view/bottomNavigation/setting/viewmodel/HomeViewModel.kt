package com.example.personalcoach.view.bottomNavigation.setting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalcoach.domain.model.SlideScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel:ViewModel() {
    private val _search = MutableLiveData<String>()
//    val sliderScreen: List<SlideScreen>
//        get() {
//            TODO()
//        }
    val searchRequest: LiveData<String>
        get()=_search

//    val trendingCourses: List<TrendingCourse>

    fun getRequest(request: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _search.value = request
                println(_search.value)
            }
        }
    }

}

data class HomeState(
    val sliderScreen: List<SlideScreen>,
    val searchRequest: String,
    val trendingCourses: List<TrendingCourse>
)

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

