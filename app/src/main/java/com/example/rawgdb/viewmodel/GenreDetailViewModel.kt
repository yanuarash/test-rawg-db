package com.example.rawgdb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rawgdb.model.GamesResults
import com.example.rawgdb.repository.Repository
import com.example.rawgdb.roomdb.Bookmark
import com.example.rawgdb.roomdb.BookmarkRepository
import kotlinx.coroutines.*

class GenreDetailViewModel constructor(private val repository: Repository, private val bookmarkRepository: BookmarkRepository) : ViewModel() {
    //mutable variable
    val errorMsg = MutableLiveData<String>()
    val genreDetailList = MutableLiveData<List<GamesResults>>()
    val bookmarkIdList = MutableLiveData<List<Int>>()
    val loading = MutableLiveData<Boolean>()

    //job
    var job: Job? = null
    val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> onError("Genre Detail Exception: ${throwable.localizedMessage}") }

    fun getGenresDetail(key: String, genre: Int, ordering: String, page: Int, page_size: Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getGenresDetail(key, genre, ordering, page, page_size)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    genreDetailList.postValue(response.body()?.results)
                    loading.value = false
                    getAllBookmarkId()
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

    //region bookmarkDao
    fun getAllBookmarkId(){
        viewModelScope.launch {
            bookmarkIdList.postValue(bookmarkRepository.getAllBookmarkId())
        }
    }

    fun insertBookmark(bookmark: Bookmark){
        viewModelScope.launch {
            bookmarkRepository.insertBookmark(bookmark)
            getAllBookmarkId()
        }
    }

    fun deleteBookmark(id: Int){
        viewModelScope.launch {
            bookmarkRepository.deleteBookmark(id)
            getAllBookmarkId()
        }
    }
    //endregion
}