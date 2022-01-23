package com.example.rawgdb.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.rawgdb.R
import com.example.rawgdb.adapter.GenreDetailAdapter
import com.example.rawgdb.api.RetrofitService
import com.example.rawgdb.databinding.ActivityGenreDetailBinding
import com.example.rawgdb.factory.GenreDetailViewModelFactory
import com.example.rawgdb.repository.Repository
import com.example.rawgdb.roomdb.BookmarkRepository
import com.example.rawgdb.viewmodel.GenreDetailViewModel
import com.google.android.material.snackbar.Snackbar

class GenreDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGenreDetailBinding
    lateinit var viewmodel: GenreDetailViewModel
    private val adapter = GenreDetailAdapter()
    private var genre: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get parameter from previous activity
        genre = intent.getIntExtra("genre", 0)
        val genreName = intent.getStringExtra("genre_name")

        val actionBar = supportActionBar
        actionBar?.title = genreName

        //set bookmarkRepo
        val bookmarkRepository = BookmarkRepository(this)

        //set retrofit
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        //set binding view
        binding.content.rvList.adapter = adapter

        //set viewmodel
        viewmodel = ViewModelProvider(
            this,
            GenreDetailViewModelFactory(mainRepository, bookmarkRepository)
        ).get(GenreDetailViewModel::class.java)

        viewmodel.genreDetailList.observe(this, {
            adapter.setListGamesResults(it)
        })

        viewmodel.bookmarkIdList.observe(this, {
            adapter.setAdapter(it, viewmodel, this)
        })

        viewmodel.errorMsg.observe(this, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            binding.content.btnReload.visibility = View.VISIBLE
            binding.content.progressBar.visibility = View.GONE
            binding.content.rvList.visibility = View.GONE
        })

        viewmodel.loading.observe(this, {
            if (it) {
                binding.content.progressBar.visibility = View.VISIBLE
                binding.content.rvList.visibility = View.GONE
            } else {
                binding.content.progressBar.visibility = View.GONE
                binding.content.rvList.visibility = View.VISIBLE
            }
        })

        viewmodel.getGenresDetail(RetrofitService.getKey(), genre, "ratings_count", 1, 10)

        binding.content.btnReload.setOnClickListener(View.OnClickListener {
            binding.content.btnReload.visibility = View.GONE
            binding.content.progressBar.visibility = View.VISIBLE
            binding.content.rvList.visibility = View.GONE
            viewmodel.getGenresDetail(RetrofitService.getKey(), genre, "ratings_count", 1, 10)
        })

    }

    override fun onResume() {
        super.onResume()
        viewmodel.getAllBookmarkId()
    }
}