package com.chaiyaphat.artbook_full_test.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.chaiyaphat.artbook_full_test.R
import com.chaiyaphat.artbook_full_test.adapter.ImageRecycleViewAdapter
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecycleViewAdapter: ImageRecycleViewAdapter
): Fragment(R.layout.fragment_image_api) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}