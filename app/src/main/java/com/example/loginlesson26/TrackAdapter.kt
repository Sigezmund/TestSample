package com.example.loginlesson26

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginlesson26.databinding.ItemTrackBinding

class TrackAdapter : RecyclerView.Adapter<TrackAdapter.Holder>() {

    var tracksLiveData: List<TopTrack> = emptyList()
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
        val nameTrack = tracksLiveData[position]

        with(holder.binding) {
            holder.itemView.tag = nameTrack
            val image = nameTrack.image?.get(2)
            val urlImage = image?.text
            Glide.with(photoImageView.context)
                .load(urlImage)
                .circleCrop()
                .into(photoImageView)

            artistNameTextView.text = nameTrack.artist?.name.toString()
            nameTrackTextView.text = nameTrack.name
        }
    }

    override fun getItemCount(): Int = tracksLiveData.size

    class Holder(
        val binding: ItemTrackBinding
    ) : RecyclerView.ViewHolder(binding.root)
}