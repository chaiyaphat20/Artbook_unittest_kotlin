package com.chaiyaphat.artbook_full_test.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaiyaphat.artbook_full_test.R
import com.chaiyaphat.artbook_full_test.adapter.ArtRecycleAdapter
import com.chaiyaphat.artbook_full_test.databinding.FragmentArtsBinding
import com.chaiyaphat.artbook_full_test.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    private val artRecycleAdapter: ArtRecycleAdapter
) : Fragment(R.layout.fragment_arts) {
    private var fragmentBindings: FragmentArtsBinding? = null
    lateinit var viewModel: ArtViewModel

    private val swipeCallBack =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition
                val selectedArt = artRecycleAdapter.arts[layoutPosition]
                viewModel.deleteArt(selectedArt)
            }

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArtsBinding.bind(view)
        fragmentBindings = binding

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        subscriptToObserves()

        binding.recyclerViewArt.adapter = artRecycleAdapter
        binding.recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())

        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }

    private fun subscriptToObserves() {
        viewModel.artList.observe(viewLifecycleOwner) {
            artRecycleAdapter.arts = it
        }
    }

    override fun onDestroy() {
        fragmentBindings = null
        super.onDestroy()
    }
}