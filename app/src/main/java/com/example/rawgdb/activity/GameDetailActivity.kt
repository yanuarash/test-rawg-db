package com.example.rawgdb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.rawgdb.R
import com.example.rawgdb.api.RetrofitService
import com.example.rawgdb.databinding.ActivityGameDetailBinding
import com.example.rawgdb.factory.GameDetailViewModelFactory
import com.example.rawgdb.model.GamesResults
import com.example.rawgdb.repository.Repository
import com.example.rawgdb.roomdb.Bookmark
import com.example.rawgdb.roomdb.BookmarkRepository
import com.example.rawgdb.util.ChangeDateFormat
import com.example.rawgdb.viewmodel.GameDetailViewModel
import com.google.android.material.snackbar.Snackbar

class GameDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailBinding
    lateinit var viewmodel: GameDetailViewModel
    lateinit var data: GamesResults

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.hide()

        val id = intent.getIntExtra("id", 0)

        //set retrofit
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = Repository(retrofitService)

        //set bookmarkRepo
        val bookmarkRepository = BookmarkRepository(this)

        //set viewmodel
        viewmodel = ViewModelProvider(
            this,
            GameDetailViewModelFactory(mainRepository, bookmarkRepository)
        ).get(GameDetailViewModel::class.java)

        //region retrofit
        viewmodel.getGameDetail.observe(this, {
            binding.tvTitle.text = it.name
            binding.tvDeveloper.text = it.developers[0].name
            binding.tvRating.text = it.rating.toString()
            binding.tvYear.text = ChangeDateFormat.dateToYear(it.released)
            binding.tvDescriptionText.text = HtmlCompat.fromHtml(it.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            Glide.with(this).load(it.background_image).centerCrop().into(binding.ivMainPicture)
            data = it
        })
        viewmodel.errorMsg.observe(this, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            binding.btnReload.visibility = View.VISIBLE
            binding.nestedScrollView.visibility = View.GONE
            binding.btnBookmark.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        })
        viewmodel.loading.observe(this, {
            if (it) {
                binding.nestedScrollView.visibility = View.GONE
                binding.btnBookmark.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.nestedScrollView.visibility = View.VISIBLE
                binding.btnBookmark.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        })
        //endregion

        viewmodel.bookmarkResult.observe(this, {
            if(it != null){
                binding.btnBookmark.text = "Delete from Bookmark"
                binding.btnBookmark.setOnClickListener(View.OnClickListener {
                    deleteFromBookmark(id)
                })
            }else{
                val newData = Bookmark(
                    id = data.id,
                    name = data.name,
                    rating = data.rating,
                    released = data.released,
                    background = data.background_image
                )
                binding.btnBookmark.text = "Add to Bookmark"
                binding.btnBookmark.setOnClickListener(View.OnClickListener{
                    addToBookmark(newData)
                })
            }
        })


        viewmodel.getGameDetail(RetrofitService.getKey(), id)

        binding.btnReload.setOnClickListener(View.OnClickListener {
            binding.btnReload.visibility = View.GONE
            binding.nestedScrollView.visibility = View.GONE
            binding.btnBookmark.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            viewmodel.getGameDetail(RetrofitService.getKey(), id)
        })
    }

    fun addToBookmark(data: Bookmark){
        viewmodel.insertBookmark(data)
        Snackbar.make(binding.root, "Added to Bookmark", Snackbar.LENGTH_SHORT).show()
    }

    fun deleteFromBookmark(id: Int){
        viewmodel.deleteBookmark(id)
        Snackbar.make(binding.root, "Deleted from Bookmark", Snackbar.LENGTH_SHORT).show()
    }


}