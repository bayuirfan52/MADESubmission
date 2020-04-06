package com.bayuirfan.madesubmission.features.dashboard.search

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.adapter.TvShowRecyclerAdapter
import com.bayuirfan.madesubmission.features.dashboard.tvshow.TvShowViewModel
import com.bayuirfan.madesubmission.features.details.tvshow.DetailTvShowActivity
import com.bayuirfan.madesubmission.model.data.*
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_DETAIL
import com.bayuirfan.madesubmission.utils.OnItemClickCallback
import kotlinx.android.synthetic.main.activity_search_tv_show.*

class SearchTvShowActivity : AppCompatActivity(), OnItemClickCallback<TvShowModel> {

    private lateinit var adapter: TvShowRecyclerAdapter
    private val tvShowModel = mutableListOf<TvShowModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_tv_show)
        actionBar?.title = getString(R.string.search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rv_search.layoutManager = LinearLayoutManager(this)
        adapter = TvShowRecyclerAdapter(this, tvShowModel as ArrayList<TvShowModel>, this)
        rv_search.adapter = adapter
        rv_search.setHasFixedSize(true)
        showError()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchMenu = menu?.findItem(R.id.search_button)
        val searchView = searchMenu?.actionView as SearchView

        searchView.isIconified = false

        searchView.setOnQueryTextListener ( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let { searchData(it) }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null){
                    if (p0.isNotEmpty()){
                        searchData(p0)
                    }
                }

                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClicked(model: TvShowModel) {
        val intent = Intent(this, DetailTvShowActivity::class.java)
        intent.putExtra(EXTRA_DETAIL, model)
        startActivity(intent)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        finish()
    }

    private fun searchData(name: String){
        val tvShowModel = ViewModelProvider(this).get(TvShowViewModel::class.java)
        tvShowModel.searchTvShowByName(name).observe(this, observer)
        showLoading(true)
    }

    private val observer: Observer<Discover<TvShowModel>> = Observer { data ->
        showLoading(false)
        if (data != null){
            if (data.status_code == null){
                tvShowModel.clear()
                tvShowModel.addAll(data.results)
                adapter.notifyDataSetChanged()
                hideError()
            } else {
                showError()
            }
        } else {
            showError()
        }
    }

    private fun showLoading(boolean: Boolean){
        if (boolean){
            hideError()
            search_loading.visibility = View.VISIBLE
            rv_search.visibility = View.INVISIBLE
        } else {
            search_loading.visibility = View.GONE
        }
    }

    private fun showError(){
        iv_src_not_found.visibility = View.VISIBLE
        tv_src_not_found.visibility = View.VISIBLE
        rv_search.visibility = View.INVISIBLE
    }

    private fun hideError(){
        iv_src_not_found.visibility = View.GONE
        tv_src_not_found.visibility = View.GONE
        rv_search.visibility = View.VISIBLE
    }
}