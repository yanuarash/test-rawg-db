package com.example.rawgdb.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rawgdb.repository.Repository
import com.example.rawgdb.roomdb.BookmarkDao
import com.example.rawgdb.roomdb.BookmarkRepository
import com.example.rawgdb.viewmodel.BookmarkViewModel
import com.example.rawgdb.viewmodel.GameDetailViewModel
import com.example.rawgdb.viewmodel.GenreDetailViewModel
import com.example.rawgdb.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class MainViewModelFactory constructor(private val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            MainViewModel(this.repository) as T
        }else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

class GenreDetailViewModelFactory constructor(private val repository: Repository, private val bookmarkRepository: BookmarkRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(GenreDetailViewModel::class.java)){
            GenreDetailViewModel(this.repository, this.bookmarkRepository) as T
        }else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

class GameDetailViewModelFactory constructor(private val repository: Repository, private val bookmarkRepository: BookmarkRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(GameDetailViewModel::class.java)){
            GameDetailViewModel(this.repository, this.bookmarkRepository) as T
        }else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}


class BookmarkViewModelFactory constructor(private val bookmarkRepository: BookmarkRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(BookmarkViewModel::class.java)){
            BookmarkViewModel(this.bookmarkRepository) as T
        }else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}