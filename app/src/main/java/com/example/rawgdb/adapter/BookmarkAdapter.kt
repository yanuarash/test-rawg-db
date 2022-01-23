package com.example.rawgdb.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rawgdb.activity.GameDetailActivity
import com.example.rawgdb.databinding.BookmarkRvListBookmarkBinding
import com.example.rawgdb.roomdb.Bookmark
import com.example.rawgdb.util.ChangeDateFormat

class BookmarkAdapter : RecyclerView.Adapter<BookmarkViewHolder>() {
    var bookmarkList = mutableListOf<Bookmark>()
    private lateinit var context: Context

    fun setAdapter(bookmarkList: List<Bookmark>, context: Context) {
        this.bookmarkList = bookmarkList.toMutableList()
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BookmarkRvListBookmarkBinding.inflate(inflater, parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val data = bookmarkList[position]
        holder.binding.tvTitle.text = data.name
        holder.binding.tvYear.text = ChangeDateFormat.dateToYear(data.released)
        Glide.with(context).load(data.background).centerCrop().into(holder.binding.imageView)
        holder.binding.cv.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, GameDetailActivity::class.java)
            intent.putExtra("id", data.id)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }
}

class BookmarkViewHolder(val binding: BookmarkRvListBookmarkBinding): RecyclerView.ViewHolder(binding.root)
