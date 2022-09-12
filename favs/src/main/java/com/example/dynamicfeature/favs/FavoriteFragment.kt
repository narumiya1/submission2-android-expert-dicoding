package com.example.dynamicfeature.favs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dynamicfeature.favs.databinding.FragmentFavsBinding
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private var fragmentFavBindign : FragmentFavsBinding?=null
    private val binding get() = fragmentFavBindign!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFavBindign = FragmentFavsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        val sectionsPagerAdapter = SectionsPagerAdapter(context as Context, childFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tab.setupWithViewPager(binding.viewPager)
    }
}