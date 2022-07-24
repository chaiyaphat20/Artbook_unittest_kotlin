package com.chaiyaphat.artbook_full_test.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chaiyaphat.artbook_full_test.R
import com.chaiyaphat.artbook_full_test.adapter.ImageRecycleViewAdapter
import com.chaiyaphat.artbook_full_test.databinding.FragmentImageApiBinding
import com.chaiyaphat.artbook_full_test.util.Status
import com.chaiyaphat.artbook_full_test.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecycleViewAdapter: ImageRecycleViewAdapter
) : Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: ArtViewModel

    private var fragmentBindings: FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBindings = binding

        var job:Job? = null
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch{
                delay(1000)
                it.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }


        subscriptToObservers()

        binding.imageRecyclerView.adapter = imageRecycleViewAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        imageRecycleViewAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }
    }

    fun subscriptToObservers() {
        viewModel.images.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val surls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }
                    imageRecycleViewAdapter.images = surls ?: listOf()
                    fragmentBindings?.progressBar?.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?:"Error",Toast.LENGTH_LONG).show()
                    fragmentBindings?.progressBar?.visibility = View.GONE
                }

                Status.LOADING -> {
                    fragmentBindings?.progressBar?.visibility = View.VISIBLE
                }
            }
        }
    }

}