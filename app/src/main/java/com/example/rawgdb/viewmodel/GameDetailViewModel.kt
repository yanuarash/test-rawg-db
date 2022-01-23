package com.example.rawgdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rawgdb.model.GamesResults
import com.example.rawgdb.repository.Repository
import com.example.rawgdb.roomdb.Bookmark
import com.example.rawgdb.roomdb.BookmarkRepository
import kotlinx.coroutines.*

class GameDetailViewModel constructor(private val repository: Repository, private val bookmarkRepository: BookmarkRepository): ViewModel() {
    //mutable variable
    val errorMsg = MutableLiveData<String>()
    val getGameDetail = MutableLiveData<GamesResults>()
    val loading = MutableLiveData<Boolean>()

    //livedata Bookmark
    var bookmarkResult = MutableLiveData<Bookmark>()

    //job
    var job: Job? = null
    val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> onError("Genre Detail Exception: ${throwable.localizedMessage}") }

    //region Retrofit
    fun getGameDetail(key: String, id: Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getGameDetail(key, id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    getGameDetail.postValue(response.body())
                    loading.value = false
                    getBookmark(id)
                }else {
                    onError("Error Genre: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        loading.postValue(false)
        errorMsg.postValue(message)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
    //endregion

    //region bookmarkDao
    fun insertBookmark(bookmark: Bookmark){
        viewModelScope.launch {
            bookmarkRepository.insertBookmark(bookmark)
            getBookmark(bookmark.id)
            bookmarkResult.postValue(bookmark)
        }
    }

    fun deleteBookmark(id: Int){
        viewModelScope.launch {
            bookmarkRepository.deleteBookmark(id)
            bookmarkResult.value = null
        }
    }

    fun getBookmark(id: Int){
        viewModelScope.launch {
            bookmarkResult.postValue(bookmarkRepository.getBookmark(id))
        }
    }
    //endregion
}