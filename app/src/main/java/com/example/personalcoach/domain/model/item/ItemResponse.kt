package com.example.personalcoach.domain.model.item

data class ItemResponse(
    val item: Item,
    val url: MutableList<String>
)
data class Item(
    val id: Long? = null,
    val name: String? = null,
    val price: Float = 0f,
    val rating: Float = 0f,

    val typeId: Type? = null,

    val brandId: Brand? = null
)

data class Type(
    val id: Long?,
    val name: String?
)

data class Brand(
    val id: Long?,
    val name: String?
)
