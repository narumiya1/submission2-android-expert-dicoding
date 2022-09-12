package com.example.dynamicfeature.favs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicfeature.favs.databinding.FragmentFavsBinding
import com.example.dynamicfeature.favs.databinding.FragmentFavsGhiblBinding
import com.example.submission2fx.DetailActivity
import com.example.submission2fx.core.domain.model.GhibliMv
import com.example.submission2fx.core.ui.MoviesAdapter
import com.example.submission2fx.core.utils.SortUtils
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class FavsMovsFragment : Fragment(){

    private var fragmentFavsGhiblBinding : FragmentFavsGhiblBinding? = null
    private val binding get() = fragmentFavsGhiblBinding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        fragmentFavsGhiblBinding =
            FragmentFavsGhiblBinding.inflate(inflater, container, false)
        return binding.root
    }


    private lateinit var moviesAdapter: MoviesAdapter
    private val viewModel: FavsViewModel by viewModel()
    private var sort = SortUtils.RANDOM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding.rvFavoriteMovies)

        moviesAdapter = MoviesAdapter()

        binding.progressBar.visibility = View.VISIBLE
        binding.notFound.visibility = View.GONE
        binding.notFoundText.visibility = View.GONE

        viewModel.getFavoriteMovies().observe(this, moviesObserver)

        with(binding.rvFavoriteMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = moviesAdapter
        }

        moviesAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

        binding.random.setOnClickListener {
            binding.menu.close(true)
            sort = SortUtils.RANDOM
        }
        binding.newest.setOnClickListener {
            binding.menu.close(true)
            sort = SortUtils.NEWEST
        }
        binding.popularity.setOnClickListener {
            binding.menu.close(true)
            sort = SortUtils.POPULARITY
        }
        binding.vote.setOnClickListener {
            binding.menu.close(true)
            sort = SortUtils.VOTE
        }

    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movie = moviesAdapter.getSwipedData(swipedPosition)
                var state = movie.favorite
                viewModel.setFavorite(movie, !state)
                state = !state
                val snackBar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_ok) {
                    viewModel.setFavorite(movie, !state)
                }
                snackBar.show()
            }
        }
    })

    private val moviesObserver = Observer<List<GhibliMv>> { movies ->
        if (movies.isNullOrEmpty()){
            binding.progressBar.visibility = View.GONE
            binding.notFound.visibility = View.VISIBLE
            binding.notFoundText.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.notFound.visibility = View.GONE
            binding.notFoundText.visibility = View.GONE
        }
        moviesAdapter.setData(movies)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentFavsGhiblBinding = null
    }

}