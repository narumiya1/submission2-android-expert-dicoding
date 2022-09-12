package com.example.submission2fx.core.ui

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.submission2fx.core.R
import com.example.submission2fx.core.databinding.ItemListCardBinding
import com.example.submission2fx.core.domain.model.GhibliMv
import com.example.submission2fx.core.utils.DiffUtils
import com.squareup.picasso.Picasso
import java.util.ArrayList

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var listData = ArrayList<GhibliMv>()
    var onItemClick: ((GhibliMv) -> Unit)? = null

    fun setData(newListData: List<GhibliMv>?) {
        if (newListData == null) return

        Log.d("","ghiblimv" +listData)

        val diffUtilCallback = DiffUtils(listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)

    }

    fun getSwipedData(swipedPosition: Int): GhibliMv = listData[swipedPosition]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemCardListBinding =
           ItemListCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemCardListBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        Log.d("","ghiblimv" +listData)

        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class MovieViewHolder(private val binding: ItemListCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: GhibliMv) {
            Log.d("","ghiblimv" +movie)
            with(binding) {
                title.text = movie.title
                date.text = movie.release_date
                subTitle.text = movie.original_title_romanised
                popularity.text =
                itemView.context.getString(
                        R.string.score,
                        movie.rt_score.toString()
                    )

                Picasso.get().load(movie.image).into(poster)
                progressBar.visibility= GONE

            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}