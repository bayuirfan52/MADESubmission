package com.bayuirfan.madesubmission.features.favorites


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.adapter.MainPagerAdapter
import com.bayuirfan.madesubmission.features.dashboard.movie.MovieFragment
import com.bayuirfan.madesubmission.features.dashboard.tvshow.TvShowFragment
import com.bayuirfan.madesubmission.utils.Constant.LOAD_FROM_LOCAL_STORAGE
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(activity as AppCompatActivity){
            val adapter = MainPagerAdapter(supportFragmentManager)
            adapter.add(MovieFragment.getInstance(LOAD_FROM_LOCAL_STORAGE), resources.getString(R.string.movie))
            adapter.add(TvShowFragment.getInstance(LOAD_FROM_LOCAL_STORAGE), resources.getString(R.string.tv_show))
            vp_favorite.adapter = adapter
            tl_favorite.setupWithViewPager(vp_favorite)
        }
    }
}