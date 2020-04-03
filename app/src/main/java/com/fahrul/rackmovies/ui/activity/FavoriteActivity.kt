package com.fahrul.rackmovies.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.adapter.ViewPagerAdapter
import com.fahrul.rackmovies.ui.fragment.FavoriteMoviesFragment
import com.fahrul.rackmovies.ui.fragment.FavoriteTvFragment
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite)

        setViewPager()
    }

    private fun setViewPager() {
        val fragmentList = listOf(
            FavoriteMoviesFragment(),
            FavoriteTvFragment()
        )

        val titleList = listOf(
            getString(R.string.tab_text_1),
            getString(R.string.tab_text_2)
        )

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, fragmentList, titleList)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> true
        }
    }
}
