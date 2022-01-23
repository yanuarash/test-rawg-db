package com.example.rawgdb.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rawgdb.activity.GenreDetailActivity
import com.example.rawgdb.databinding.MainactivityRvListGenreBinding
import com.example.rawgdb.model.GenreResults

class GenreAdapter : RecyclerView.Adapter<MainViewHolder>() {
    var genreList = mutableListOf<GenreResults>()
    private lateinit var context: Context

    fun setAdapter(genres: List<GenreResults>, context: Context) {
        this.genreList = genres.toMutableList()
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainactivityRvListGenreBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val genre = genreList[position]
        holder.binding.tvGenre.text = genre.name
        holder.binding.cv.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, GenreDetailActivity::class.java)
            intent.putExtra("genre", genre.id)
            intent.putExtra("genre_name", genre.name)
            context.startActivity(intent)
        })

        Glide.with(context).load(genre.image_background).centerCrop().into(holder.binding.imageView)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }
}

class MainViewHolder(val binding: MainactivityRvListGenreBinding) : RecyclerView.ViewHolder(binding.root)