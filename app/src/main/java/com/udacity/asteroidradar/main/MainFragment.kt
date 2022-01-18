package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.AsteroidRadarApplication
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels {
        // Use ViewModelFactory and get Dao from Application
        AsteroidViewModelFactory((activity?.application as AsteroidRadarApplication).database.asteroidDao())
    }

    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPictureOfTheDay()
        // RecyclerView
        // Adapter

        viewModel.asteroids.observe(this.viewLifecycleOwner) { items ->
            val all = items
            // TODO Add to adapter
        }

        viewModel.pictureOfDay.observe(viewLifecycleOwner, {
            Glide.with(this).load(it.url)
                .centerCrop()
                .into(binding.activityMainImageOfTheDay)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
