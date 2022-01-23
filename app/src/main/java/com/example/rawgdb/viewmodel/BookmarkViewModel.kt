package com.example.rawgdb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rawgdb.roomdb.Bookmark
import com.example.rawgdb.roomdb.BookmarkRepository
import kotlinx.coroutines.launch

class BookmarkViewModel constructor(private val bookmarkRepository: BookmarkRepository): ViewModel(){
    val getListBookmark = MutableLiveData<List<Bookmark>>()

    fun getBookmark(){
        viewModelScope.launch {
            getListBookmark.value = bookmarkRepository.getAllBookmark()
        }
    }

}