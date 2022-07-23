package com.chaiyaphat.artbook_full_test.repo

import androidx.lifecycle.LiveData
import com.chaiyaphat.artbook_full_test.model.ImageResponse
import com.chaiyaphat.artbook_full_test.roomdb.Art
import com.chaiyaphat.artbook_full_test.util.Resource

interface ArtRepositoryInterface {
    suspend fun insert(art: Art)
    suspend fun deleteArt(art: Art)
    fun getArt(): LiveData<List<Art>>
    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}