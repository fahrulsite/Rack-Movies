package com.fahrul.consumerrackmovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fahrul.consumerrackmovies.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = supportActionBar
        toolbar?.elevation = 0f
        setViewPager()

    }

    private fun setViewPager() {
        val listFragment = listOf(
            MovieFragment(),
            TVFragment()
        )

        val titleFragment = listOf(
            getString(R.string.movie),
            getString(R.string.tv)
        )

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, listFragment, titleFragment)
        tabLayout.setupWithViewPager(viewPager)
    }


}
