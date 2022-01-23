package com.example.rawgdb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.rawgdb.R
import com.example.rawgdb.adapter.BookmarkAdapter
import com.example.rawgdb.databinding.ActivityBookmarkBinding
import com.example.rawgdb.factory.BookmarkViewModelFactory
import com.example.rawgdb.factory.GameDetailViewModelFactory
import com.example.rawgdb.roomdb.BookmarkRepository
import com.example.rawgdb.viewmodel.BookmarkViewModel
import com.example.rawgdb.viewmodel.GameDetailViewModel

class BookmarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkBinding
    lateinit var viewmodel: BookmarkViewModel
    private val adapter = BookmarkAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.bookmark)

        //set bookmarkRepo
        val bookmarkRepository = BookmarkRepository(this)

        //set binding view
        binding.rvListBookmark.adapter = adapter

        //set viewmodel
        viewmodel = ViewModelProvider(
            this,
            BookmarkViewModelFactory(bookmarkRepository)
        ).get(BookmarkViewModel::class.java)

        //observe list bookmark
        viewmodel.getListBookmark.observe(this, {
            if(it.isNotEmpty()){
                adapter.setAdapter(it, this)
                binding.rvListBookmark.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
            }else{
                binding.rvListBookmark.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            }
        })

        //get bookmark
        viewmodel.getBookmark()
    }

    override fun onResume() {
        super.onResume()

        //get bookmark
        viewmodel.getBookmark()
    }
}