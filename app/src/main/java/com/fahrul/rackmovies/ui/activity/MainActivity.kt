package com.fahrul.rackmovies.ui.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.adapter.ViewPagerAdapter
import com.fahrul.rackmovies.ui.fragment.MoviesFragment
import com.fahrul.rackmovies.ui.fragment.TVFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_QUERY = "extra_query"
        const val EXTRA_TYPE = "extra_type"
        const val TYPE_MOVIE = "Movie"
        const val TYPE_TV_SHOW = "Tv show"
    }

    private var selectedType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.show()
        supportActionBar?.elevation = 0f

        setViewPager()
    }

    private fun setViewPager() {
        val fragmentList = listOf(
            MoviesFragment(),
            TVFragment()
        )

        val titleList = listOf(
            getString(R.string.tab_text_1),
            getString(R.string.tab_text_2)
        )

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, fragmentList, titleList)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        selectedType = TYPE_MOVIE
        tabLayout.addOnTabSelectedListener(object :
            TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                searchView.queryHint = getString(R.string.search_movie)
                selectedType = TYPE_MOVIE
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        searchView.queryHint = getString(R.string.search_movie)
                        selectedType = TYPE_MOVIE
                    }
                    else -> {
                        searchView.queryHint = getString(R.string.search_tv_show)
                        selectedType = TYPE_TV_SHOW
                    }
                }
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val intent = Intent(this@MainActivity, SearchActivity::class.java)

                    intent.putExtra(EXTRA_QUERY, query)
                    intent.putExtra(EXTRA_TYPE, selectedType)

                    startActivity(intent)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ic_Language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.ic_Reminder -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            R.id.icFavorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }

            else -> true
        }
    }


}
