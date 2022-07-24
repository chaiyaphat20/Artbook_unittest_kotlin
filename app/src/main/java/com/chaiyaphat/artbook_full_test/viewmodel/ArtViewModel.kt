package com.chaiyaphat.artbook_full_test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaiyaphat.artbook_full_test.model.ImageResponse
import com.chaiyaphat.artbook_full_test.repo.ArtRepository
import com.chaiyaphat.artbook_full_test.repo.ArtRepositoryInterface
import com.chaiyaphat.artbook_full_test.roomdb.Art
import com.chaiyaphat.artbook_full_test.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(private val repository: ArtRepositoryInterface) : ViewModel() {
    //Art Fragment
    val artList = repository.getArt()


    //Image API Fragment
    private val _images = MutableLiveData<Resource<ImageResponse>>()
    val images: LiveData<Resource<ImageResponse>>
        get() = _images

    private val _selectedImage = MutableLiveData<String>()
    val selectedImage: LiveData<String>
        get() = _selectedImage


    //art details Fragment
    private var _insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMsg: LiveData<Resource<Art>>
        get() = _insertArtMsg

    fun resetInsertArtMsg() {
        _insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String) {
        _selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) {
        viewModelScope.launch {
            repository.deleteArt(art)
        }
    }

    fun insertArt(art: Art) {
        viewModelScope.launch {
            repository.insert(art)
        }
    }

    fun makeArt(name: String, artistName: String, year: String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            _insertArtMsg.postValue(Resource.error("Enter name, artist, year", null))
            return
        }
        val yearInt = try {
            year.toInt()
        } catch (E: Exception) {
            _insertArtMsg.postValue(Resource.error("Year should be number", null))
            return
        }

        val art = Art(name, artistName, yearInt, _selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        _insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }
        _images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            _images.value = response
        }
    }

}