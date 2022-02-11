package com.example.loginlesson26.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginlesson26.databinding.ItemTrackBinding
import com.example.loginlesson26.domain.TrackEntity

class TrackAdapter : RecyclerView.Adapter<TrackAdapter.Holder>() {

    var tracks: List<TrackEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrackBinding.inflate(inflater, parent, false)
        binding.root
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val nameTrack = tracks[position]

        with(holder.binding) {
            holder.itemView.tag = nameTrack

            Glide.with(photoImageView.context)
                .load(nameTrack.image)
                .circleCrop()
                .into(photoImageView)

            artistNameTextView.text = nameTrack.artist
            nameTrackTextView.text = nameTrack.name
        }
    }

    override fun getItemCount(): Int = tracks.size

    class Holder(
        val binding: ItemTrackBinding
    ) : RecyclerView.ViewHolder(binding.root)
}