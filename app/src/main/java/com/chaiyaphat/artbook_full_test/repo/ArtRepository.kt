package com.chaiyaphat.artbook_full_test.repo

import androidx.lifecycle.LiveData
import com.chaiyaphat.artbook_full_test.api.RetrofitAPI
import com.chaiyaphat.artbook_full_test.model.ImageResponse
import com.chaiyaphat.artbook_full_test.roomdb.Art
import com.chaiyaphat.artbook_full_test.roomdb.ArtDao
import com.chaiyaphat.artbook_full_test.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {
    override suspend fun insert(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }
}