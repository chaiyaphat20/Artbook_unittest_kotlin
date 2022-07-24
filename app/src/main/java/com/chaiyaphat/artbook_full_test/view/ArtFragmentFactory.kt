package com.chaiyaphat.artbook_full_test.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.chaiyaphat.artbook_full_test.adapter.ArtRecycleAdapter
import com.chaiyaphat.artbook_full_test.adapter.ImageRecycleViewAdapter
import javax.inject.Inject

class ArtFragmentFactor @Inject constructor(
    private val artRecycleAdapter: ArtRecycleAdapter,
    private val glide: RequestManager,
    private val imageRecycleViewAdapter: ImageRecycleViewAdapter,
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ArtFragment::class.java.name -> ArtFragment(artRecycleAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecycleViewAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}