package com.chaiyaphat.artbook_full_test.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.chaiyaphat.artbook_full_test.R
import com.chaiyaphat.artbook_full_test.databinding.FragmentArtDetailsBinding
import com.chaiyaphat.artbook_full_test.util.Status
import com.chaiyaphat.artbook_full_test.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_art_details) {

    lateinit var viewModel: ArtViewModel

    private var fragmentBindings: FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBindings = binding

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        subscribeToObservers()

        binding.artImageView.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }


        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener {
            viewModel.makeArt(
                binding.nameText.text.toString(),
                binding.artistText.text.toString(),
                binding.yearText.text.toString()
            )
        }

    }

    private fun subscribeToObservers() {
        viewModel.selectedImage.observe(viewLifecycleOwner) { url ->
            fragmentBindings?.let {
                glide.load(url).into(it.artImageView)
            }
        }

        viewModel.insertArtMsg.observe(viewLifecycleOwner){
           when(it.status){
               Status.SUCCESS ->{
                   Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                   findNavController().popBackStack()
                   viewModel.resetInsertArtMsg()
               }
               Status.ERROR ->{
                   Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
               }

               Status.LOADING ->{

               }

           }
        }
    }

    override fun onDestroy() {
        fragmentBindings = null
        super.onDestroy()
    }
}