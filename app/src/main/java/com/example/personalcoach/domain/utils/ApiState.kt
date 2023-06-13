package com.example.personalcoach.domain.utils

import com.example.personalcoach.domain.model.item.ItemResponse

sealed class ApiState {
    class Success(val data: List<ItemResponse>) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading:ApiState()
    object Empty: ApiState()
}
