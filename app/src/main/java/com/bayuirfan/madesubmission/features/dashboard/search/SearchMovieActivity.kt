package com.bayuirfan.madesubmission.features.dashboard.search

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.*
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.adapter.MovieRecyclerAdapter
import com.bayuirfan.madesubmission.features.dashboard.movie.MovieViewModel
import com.bayuirfan.madesubmission.features.details.movie.DetailMovieActivity
import com.bayuirfan.madesubmission.model.data.*
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_DETAIL
import com.bayuirfan.madesubmission.utils.OnItemClickCallback
import kotlinx.android.synthetic.main.activity_search_movie.*

class SearchMovieActivity : AppCompatActivity(), OnItemClickCallback<MovieModel> {

    private lateinit var adapter: MovieRecyclerAdapter
    private lateinit var movieModel: MutableList<MovieModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.title = getString(R.string.search)
        setContentView(R.layout.activity_search_movie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        movieModel = mutableListOf()

        rv_search.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adapter = MovieRecyclerAdapter(this, movieModel as ArrayList<MovieModel>, this)
        rv_search.adapter = adapter
        rv_search.setHasFixedSize(true)
        showError()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchMenu = menu?.findItem(R.id.search_button)
        val searchView = searchMenu?.actionView as SearchView

        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchData(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    if (newText.isNotEmpty()){
                        searchData(newText)
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

    override fun onItemClicked(model: MovieModel) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(EXTRA_DETAIL, model)
        startActivity(intent)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        finish()
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

    private fun searchData(name: String){
        val movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.searchMovieByName(name).observe(this,observer)
        showLoading(true)
    }

    private val observer : Observer<Discover<MovieModel>> = Observer {data ->
        showLoading(false)
        if (data != null){
            if (data.status_code == null){
                movieModel.clear()
                movieModel.addAll(data.results)
                adapter.notifyDataSetChanged()
                hideError()
            } else {
                showError()
            }
        } else {
            showError()
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