package com.example.submission2fx.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.example.submission2fx.R
import com.example.submission2fx.core.domain.model.GhibliMv
import com.example.submission2fx.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity() {



    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailMovie = intent.getParcelableExtra<GhibliMv>(EXTRA_MOVIE)
        if (detailMovie != null) {
            populateDetail(detailMovie)
        }

        binding.backButton.setOnClickListener { onBackPressed() }
        binding.share.setOnClickListener { share() }
    }

    private fun populateDetail(movie: GhibliMv) {
        with(binding) {
            titleDetail.text = movie.title
            date.text = movie.original_title_romanised
            overview.text = movie.description
            popularity.text = getString(
                R.string.popularity_detail,
                movie.rt_score.toString(),
                movie.running_time.toString(),
                movie.release_date
            )

            Picasso.get().load(movie.image).into(posterTopBar)
            Picasso.get().load(movie.movie_banner).into(subPoster)


            var favoriteState = movie.favorite
            setFavoriteState(favoriteState)
            binding.favoriteButton.setOnClickListener {
                favoriteState = !favoriteState
                viewModel.setFavoriteMovie(movie, favoriteState)
                setFavoriteState(favoriteState)
            }
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
        } else {
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_border
                )
            )
        }
    }

    private fun share() {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder.from(this).apply {
            setType(mimeType)
            setChooserTitle(getString(R.string.shareTitle))
            setText(getString(R.string.shareBody))
            startChooser()
        }
    }

    companion object {
        const val EXTRA_MOVIE = "extraMovie"
    }
}