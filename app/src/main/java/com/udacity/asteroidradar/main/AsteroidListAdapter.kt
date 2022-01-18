package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.databinding.ItemAsteroidListBinding

class AsteroidListAdapter(private val onItemClicked: (Asteroid) -> Unit)
    : ListAdapter<Asteroid, AsteroidListAdapter.AsteroidListViewHolder>(DiffCallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : AsteroidListAdapter.AsteroidListViewHolder {
        return AsteroidListViewHolder(ItemAsteroidListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: AsteroidListAdapter.AsteroidListViewHolder,
        position: Int)
    {
        val asteroid = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(asteroid)
        }
        holder.bind(asteroid)
    }

    class AsteroidListViewHolder(private var binding: ItemAsteroidListBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.isPotentiallyHazardous == newItem.isPotentiallyHazardous
                    && oldItem.absoluteMagnitude == newItem.absoluteMagnitude
                    && oldItem.closeApproachDate == newItem.closeApproachDate
                    && oldItem.codename == newItem.codename
                    && oldItem.distanceFromEarth == newItem.distanceFromEarth
                    && oldItem.estimatedDiameter == newItem.estimatedDiameter
                    && oldItem.relativeVelocity == newItem.relativeVelocity
        }
    }
}