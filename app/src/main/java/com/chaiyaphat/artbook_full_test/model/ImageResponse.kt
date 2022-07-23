package com.chaiyaphat.artbook_full_test.model

data class ImageResponse(
    val hits:List<ImageResult>,
    val total:Int,
    val totalHints:Int
)