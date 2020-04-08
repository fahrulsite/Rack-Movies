package com.fahrul.rackmovies.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fahrul.rackmovies.R
import com.fahrul.rackmovies.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = listOf(
            FavoriteMoviesFragment(),
            FavoriteTvFragment()
        )

        val titleList = listOf(
            getString(R.string.movie),
            getString(R.string.tv)
        )

        viewPager.adapter = ViewPagerAdapter(childFragmentManager, fragmentList, titleList)
        tabLayout.setupWithViewPager(viewPager)
    }
}
