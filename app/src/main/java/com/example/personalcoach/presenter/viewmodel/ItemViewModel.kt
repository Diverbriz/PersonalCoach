package com.example.personalcoach.presenter.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalcoach.domain.model.item.ItemResponse
import com.example.personalcoach.data.network.RestClient
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ItemViewModel : ViewModel(){
    var _state = mutableStateListOf<ItemResponse>()

    init {
        fetchBooks()
    }

    fun fetchBooks(name: String = "",brandName: String = "",typeName: String = "") {

    }
}