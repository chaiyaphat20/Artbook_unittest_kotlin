package com.chaiyaphat.artbook_full_test.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaiyaphat.artbook_full_test.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var fragmentFactory: ArtFragmentFactor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}