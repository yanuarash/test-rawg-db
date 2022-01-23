package com.example.rawgdb.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rawgdb.R
import com.example.rawgdb.activity.GameDetailActivity
import com.example.rawgdb.activity.GenreDetailActivity
import com.example.rawgdb.databinding.GenredetailRvListGenreDetailBinding
import com.example.rawgdb.model.GamesResults
import com.example.rawgdb.roomdb.Bookmark
import com.example.rawgdb.util.ChangeDateFormat
import com.example.rawgdb.viewmodel.GenreDetailViewModel
import com.google.android.material.snackbar.Snackbar
import okhttp3.internal.notify

class GenreDetailAdapter: RecyclerView.Adapter<GendreDetailViewHolder>(){
    var list = mutableListOf<GamesResults>()
    var listBookmark = mutableListOf<Int>()
    lateinit var viewModel: GenreDetailViewModel
    lateinit var context: Context

    fun setListGamesResults(listParam: List<GamesResults>){
        this.list = listParam.toMutableList()
        if(listBookmark.size > 0){
            notifyDataSetChanged()
        }
    }

    fun setAdapter(listBookmark: List<Int>, viewModel: GenreDetailViewModel, context: Context){
        this.listBookmark = listBookmark.toMutableList()
        this.viewModel = viewModel
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GendreDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GenredetailRvListGenreDetailBinding.inflate(inflater, parent, false)
        return GendreDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GendreDetailViewHolder, position: Int) {
        val detail = list[position]
        holder.binding.tvTitle.text = detail.name
        holder.binding.tvRating.text = detail.rating.toString()
        holder.binding.tvYear.text = ChangeDateFormat.dateToYear(detail.released)
        holder.binding.cv.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, GameDetailActivity::class.java)
            intent.putExtra("id", detail.id)
            context.startActivity(intent)
        })
        if(!listBookmark.contains(detail.id)){
            holder.binding.btnBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
            holder.binding.btnBookmark.setOnClickListener(View.OnClickListener {
                val data = Bookmark(
                    id = detail.id,
                    name = detail.name,
                    rating = detail.rating,
                    released = detail.released,
                    background = detail.background_image
                )
                viewModel.insertBookmark(data)
                Snackbar.make(holder.binding.root, "Added to Bookmark", Snackbar.LENGTH_SHORT).show()
            })
        }else{
            holder.binding.btnBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24)
            holder.binding.btnBookmark.setOnClickListener(View.OnClickListener {
                viewModel.deleteBookmark(detail.id)
                Snackbar.make(holder.binding.root, "Deleted from Bookmark", Snackbar.LENGTH_SHORT).show()
            })
        }
        Glide.with(context).load(detail.background_image).centerCrop().into(holder.binding.imageView)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class GendreDetailViewHolder(val binding: GenredetailRvListGenreDetailBinding) : RecyclerView.ViewHolder(binding.root)