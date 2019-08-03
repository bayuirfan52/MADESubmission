package com.bayuirfan.madesubmission.features

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.adapter.MainPagerAdapter
import com.bayuirfan.madesubmission.features.dashboard.MovieFragment
import com.bayuirfan.madesubmission.features.dashboard.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupComponent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupComponent() {
        val mainPagerAdapter = MainPagerAdapter(this.supportFragmentManager)
        mainPagerAdapter.add(MovieFragment(), resources.getString(R.string.movie))
        mainPagerAdapter.add(TvShowFragment(), resources.getString(R.string.tv_show))
        vp_main.adapter = mainPagerAdapter
        tl_main.setupWithViewPager(vp_main)
    }
}
