package com.example.submission2fx.favsz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2fx.DataState
import com.example.submission2fx.details.DetailActivity
import com.example.submission2fx.core.domain.model.GhibliMv
import com.example.submission2fx.core.ui.MoviesAdapter
import com.example.submission2fx.favsz.databinding.FragmenTesBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavsFragment : androidx.fragment.app.Fragment()  {

    private var testViewBinding : FragmenTesBinding?=null
    private val binding get() = testViewBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testViewBinding = FragmenTesBinding.inflate(inflater, container, false)
        return binding?.root
    }
    private lateinit var moviesAdapter: MoviesAdapter
    private val viewModel: FavsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(favoriteModule)

        moviesAdapter = MoviesAdapter()

        Log.d("adapter","movsadapter "+moviesAdapter)

        binding?.progressBar?.visibility = View.VISIBLE
        binding?.notFound?.visibility = View.GONE
        binding?.notFoundText?.visibility = View.GONE

        setList()

        with(binding?.rvFavoriteMovies) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = moviesAdapter
        }

        moviesAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }

    }

    private fun setList() {
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, moviesObserver)
    }

    private val moviesObserver = Observer<List<GhibliMv>> { movies ->
        if (movies.isNullOrEmpty()){
            setDataState(DataState.ERROR)
            /*binding.progressBar.visibility = View.GONE
            binding.notFound.visibility = View.VISIBLE
            binding.notFoundText.visibility = View.VISIBLE*/
        } else {
            /*binding.progressBar.visibility = View.GONE
            binding.notFound.visibility = View.GONE
            binding.notFoundText.visibility = View.GONE*/
            setDataState(DataState.SUCCESS)
        }
        moviesAdapter.setData(movies)
    }

    private fun setDataState(state: DataState) {
        when (state) {
            DataState.ERROR -> {
                binding?.progressBar?.visibility = View.GONE
                binding?.notFound?.visibility = View.VISIBLE
                binding?.notFoundText?.visibility = View.VISIBLE
            }
            DataState.LOADING -> {
                binding?.progressBar?.visibility = View.VISIBLE
                binding?.notFound?.visibility = View.GONE
                binding?.notFoundText?.visibility = View.GONE
            }
            DataState.SUCCESS -> {
                binding?.progressBar?.visibility = View.GONE
                binding?.notFound?.visibility = View.GONE
                binding?.notFoundText?.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvFavoriteMovies?.adapter = null
        testViewBinding = null
    }
}