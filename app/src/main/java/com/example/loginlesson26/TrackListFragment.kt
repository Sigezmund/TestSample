package com.example.loginlesson26

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginlesson26.databinding.FragmentTrackListBinding


class TrackListFragment : Fragment() {
    private lateinit var binding: FragmentTrackListBinding
    private lateinit var adapter: TrackAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackListBinding.inflate(inflater, container, false)
        adapter = TrackAdapter()
        viewModel.tracksLiveData.observe(viewLifecycleOwner) {
            adapter.tracksLiveData = it
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