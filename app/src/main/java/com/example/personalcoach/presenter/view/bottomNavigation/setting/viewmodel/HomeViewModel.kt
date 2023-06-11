package com.example.personalcoach.presenter.view.bottomNavigation.setting.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalcoach.domain.model.SlideScreen
import com.example.personalcoach.domain.model.item.ItemResponse
import com.example.personalcoach.data.network.RestClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class HomeViewModel:ViewModel() {

    var _state = mutableStateListOf<ItemResponse>()
    init {
        val json = "[\n" +
                "    {\n" +
                "        \"item\": {\n" +
                "            \"id\": 102,\n" +
                "            \"name\": \"Re:zero\",\n" +
                "            \"price\": 2024.0,\n" +
                "            \"rating\": 4.8,\n" +
                "            \"typeId\": {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Manga\"\n" +
                "            },\n" +
                "            \"brandId\": {\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"Remanga\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"url\": [\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Frezero1.jpg?alt=media\",\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Frezero2.jpg?alt=media&\",\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Frezero3.jpg?alt=media&\"\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"item\": {\n" +
                "            \"id\": 103,\n" +
                "            \"name\": \"Doctor Stown\",\n" +
                "            \"price\": 2024.0,\n" +
                "            \"rating\": 5.0,\n" +
                "            \"typeId\": {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Manga\"\n" +
                "            },\n" +
                "            \"brandId\": {\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"Remanga\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"url\": [\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Fstown2.jpg?alt=media\",\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Fstown1.jpg?alt=media\",\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Fstown3.jpg?alt=media\"\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"item\": {\n" +
                "            \"id\": 152,\n" +
                "            \"name\": \"Berserk\",\n" +
                "            \"price\": 2024.0,\n" +
                "            \"rating\": 5.0,\n" +
                "            \"typeId\": {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Manga\"\n" +
                "            },\n" +
                "            \"brandId\": {\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"Remanga\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"url\": [\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Fberserk2.jpg?alt=media\",\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Fberserk3.jpg?alt=media\",\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Fberserk1.jpg?alt=media\"\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"item\": {\n" +
                "            \"id\": 202,\n" +
                "            \"name\": \"Naruto\",\n" +
                "            \"price\": 20240.0,\n" +
                "            \"rating\": 5.0,\n" +
                "            \"typeId\": {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Manga\"\n" +
                "            },\n" +
                "            \"brandId\": {\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"Remanga\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"url\": [\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Fnaruto1.jpg?alt=media\",\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Fnaruto2.png?alt=media\",\n" +
                "            \"https://firebasestorage.googleapis.com/v0/b/personal-coach-c1684.appspot.com/o/itemImage%2Fnaruto3.png?alt=media\"\n" +
                "        ]\n" +
                "    }\n" +
                "]"
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<Array<ItemResponse>>() {}.type
        var list: Array<ItemResponse> = gson.fromJson(json, arrayTutorialType)

        for(i in list){
            _state.add(i)
        }
        fetchItems()
    }

    fun fetchItems(name: String = "",brandName: String = "",typeName: String = "") {
        viewModelScope.launch {

//            val call = RestClient().getService().getFilterItems(name, brandName, typeName)
//
//            if (!call.isEmpty()) {
//                call.forEach{
//                    _state.add(it)
//                    println(it.item.name)
//                }
//            }
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

