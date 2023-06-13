package com.example.personalcoach.data.network.repository

import com.example.personalcoach.data.network.ApiService
import com.example.personalcoach.domain.model.item.ItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val apiService: ApiService
){
    fun getItems(): Flow<List<ItemResponse>> = flow{
        emit(apiService.getFilterItems())
    }.flowOn(Dispatchers.IO)
}