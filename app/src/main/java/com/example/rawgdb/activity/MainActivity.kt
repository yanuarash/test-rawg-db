package com.example.rawgdb.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.rawgdb.R
import com.example.rawgdb.adapter.GenreAdapter
import com.example.rawgdb.api.RetrofitService
import com.example.rawgdb.databinding.ActivityMainBinding
import com.example.rawgdb.factory.MainViewModelFactory
import com.example.rawgdb.repository.Repository
import com.example.rawgdb.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewmodel: MainViewModel
    private val adapter = GenreAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.genre)

        //set retrofit
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        //set binding view
        binding.content.rvList.adapter = adapter

        //set viewmodel
        viewmodel = ViewModelProvider(
            this,
            MainViewModelFactory(mainRepository)
        ).get(MainViewModel::class.java)
        viewmodel.genreList.observe(this, {
            adapter.setAdapter(it, this)
        })
        viewmodel.errorMsg.observe(this, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
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

        //floating action button bookmark
        binding.fab.setOnClickListener {
            val intent = Intent(this, BookmarkActivity::class.java)
            startActivity(intent)
        }

        viewmodel.getGenres(RetrofitService.getKey(), "name", 1, 10)
    }
}