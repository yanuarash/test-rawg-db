package com.example.rawgdb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rawgdb.model.Genre
import com.example.rawgdb.model.GenreResults
import com.example.rawgdb.repository.Repository
import kotlinx.coroutines.*

class MainViewModel constructor(private val repository: Repository) : ViewModel() {
    //mutable variable
    val errorMsg = MutableLiveData<String>()
    val genreList = MutableLiveData<List<GenreResults>>()
    val loading = MutableLiveData<Boolean>()

    var job: Job? = null
    val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> onError("Genre Exception: ${throwable.localizedMessage}") }

    fun getGenres(key: String, ordering: String, page: Int, page_size: Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getGenres(key, ordering, page, page_size)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    genreList.postValue(response.body()?.results)
                    loading.value = false
                } else {
                    onError("Error Genre: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMsg.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}