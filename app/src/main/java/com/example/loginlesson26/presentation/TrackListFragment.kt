package com.example.loginlesson26.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginlesson26.data.AppDatabase
import com.example.loginlesson26.data.Repositories
import com.example.loginlesson26.databinding.FragmentTrackListBinding

class TrackListFragment : Fragment() {
    private lateinit var binding: FragmentTrackListBinding
    private lateinit var adapter: TrackAdapter

    private val viewModel by viewModelCreator {
        TrackListViewModel(
            Repositories(
                AppDatabase.build(
                    requireContext()
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackListBinding.inflate(inflater, container, false)
        viewModel.loadTrack()
        adapter = TrackAdapter()
        viewModel.tracksLiveData.observe(viewLifecycleOwner) {
            adapter.tracks = it
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    companion object {
        fun newInstance(): TrackListFragment {
            return TrackListFragment()
        }
    }
}