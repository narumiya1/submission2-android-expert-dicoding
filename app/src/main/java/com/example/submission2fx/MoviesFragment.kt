package com.example.submission2fx

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2fx.core.data.Resource
import com.example.submission2fx.core.domain.model.GhibliMv
import com.example.submission2fx.core.ui.MoviesAdapter
import com.example.submission2fx.databinding.FragmentMovsBinding
import com.example.submission2fx.details.DetailActivity
import com.example.submission2fx.home.HomrActivity
import com.example.submission2fx.home.SearchViewModel
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class MoviesFragment : Fragment(), View.OnClickListener{
    private var _fragmentMoviesBinding: FragmentMovsBinding? = null
    private val binding get() = _fragmentMoviesBinding

    private val viewModel: GhiblViewModel by viewModel()
    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var moviesAdapter: MoviesAdapter

    private lateinit var searchView: MaterialSearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMoviesBinding = FragmentMovsBinding.inflate(inflater, container, false)
        val toolbar: Toolbar = activity?.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        searchView = (activity as HomrActivity).findViewById(R.id.search_view)
        return binding?.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = MoviesAdapter()

//        setList(sort)
        viewModel.getMovies().observe(viewLifecycleOwner, movsObserver)

        observeSearchQuery()

        setSearchList()

        with(binding?.rvMovies) {
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
    }

    private fun setList() {
    }

    private val movsObserver = androidx.lifecycle.Observer<Resource<List<GhibliMv>>>{ moves->

        if (moves != null) {
            when (moves) {
                /*is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.notFound.visibility = View.GONE
                    binding.notFoundText.visibility = View.GONE
                }*/
                is Resource.Loading -> setDataState(DataState.LOADING)
                is Resource.Success -> {
                   /* binding.progressBar.visibility = View.GONE
                    binding.notFound.visibility = View.GONE
                    binding.notFoundText.visibility = View.GONE*/
                    setDataState(DataState.SUCCESS)
                    moviesAdapter.setData(moves.data)
                }
                is Resource.Error -> {
                   /*binding.progressBar.visibility = View.GONE
                    binding.notFound.visibility = View.VISIBLE
                    binding.notFoundText.visibility = View.VISIBLE*/
                    setDataState(DataState.ERROR)
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun observeSearchQuery() {
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let {
//                    searchViewModel.setSearchQuery(it)
//                }
                newText?.let { searchViewModel.setSearchQuery(it) }
                return true
            }
        })
    }

    private fun setSearchList() {
        searchViewModel.movieResult.observe(viewLifecycleOwner) { movies ->
            /*  if (movies.isNullOrEmpty()){
                  binding.progressBar.visibility = View.GONE
                  binding.notFound.visibility = View.VISIBLE
                  binding.notFoundText.visibility = View.VISIBLE*/
            if (movies.isNullOrEmpty()) {
                setDataState(DataState.ERROR)
            } else {
                /*binding.progressBar.visibility = View.GONE
                binding.notFound.visibility = View.GONE
                binding.notFoundText.visibility = View.GONE*/
                setDataState(DataState.SUCCESS)
            }
            moviesAdapter.setData(movies)
        }
        /*
           searchViewModel.movieResult.observe(viewLifecycleOwner, { movies ->
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
        })
         */
        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener{
            override fun onSearchViewShown() {
                /*binding.progressBar.visibility = View.GONE
                binding.notFound.visibility = View.GONE
                binding.notFoundText.visibility = View.GONE*/
                setDataState(DataState.SUCCESS)
            }

            override fun onSearchViewClosed() {
                /*binding.progressBar.visibility = View.GONE
                binding.notFound.visibility = View.GONE
                binding.notFoundText.visibility = View.GONE*/
                setDataState(DataState.SUCCESS)
                viewModel.getMovies().observe(viewLifecycleOwner, movsObserver)

            }
        })
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
        searchView.setOnQueryTextListener(null)
        searchView.setOnSearchViewListener(null)
        binding?.rvMovies?.adapter = null
        _fragmentMoviesBinding = null
    }

    override fun onClick(p0: View?) {
        when (view) {

        }
    }
}