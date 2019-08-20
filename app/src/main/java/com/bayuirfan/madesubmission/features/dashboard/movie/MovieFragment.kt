package com.bayuirfan.madesubmission.features.dashboard.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.adapter.MovieRecyclerAdapter
import com.bayuirfan.madesubmission.features.details.DetailMovieActivity
import com.bayuirfan.madesubmission.model.data.Discover
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.utils.Constant
import com.bayuirfan.madesubmission.utils.Constant.KEYS
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.view.*

class MovieFragment : Fragment(), MovieRecyclerAdapter.OnItemClickCallback {
    override fun onItemClicked(movieModels: MovieModel) {
        goToDetails(movieModels)
    }
    private lateinit var adapter: MovieRecyclerAdapter
    private var movieModel = mutableListOf<MovieModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_movie, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovieData()
        view.rv_movie.layoutManager = LinearLayoutManager(context)
        context?.let {
            adapter = MovieRecyclerAdapter(it, movieModel,this)
        }
        view.rv_movie.adapter = adapter
        view.rv_movie.setHasFixedSize(true)
    }

    private fun getMovieData(){
        val movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        movieViewModel.getMovieList().observe(this, getMovieList)
        showLoading(true)
    }

    private val getMovieList: Observer<Discover<MovieModel>> = Observer { data ->
        showLoading(false)
        if (data != null){
            if (data.status_code == null){
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
        iv_not_found.visibility = View.VISIBLE
        tv_not_found.visibility = View.VISIBLE
        rv_movie.visibility = View.GONE
    }

    private fun hideError(){
        iv_not_found.visibility = View.GONE
        tv_not_found.visibility = View.GONE
        rv_movie.visibility = View.VISIBLE
    }

    private fun showLoading(value: Boolean){
        if (value) {
            progress_movie.visibility = View.VISIBLE
            rv_movie.visibility = View.GONE
        } else {
            progress_movie.visibility = View.GONE
        }
    }

    private fun goToDetails(movieModel: MovieModel) {
        val intent = Intent(activity, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_DETAIL, movieModel)
        intent.putExtra(Constant.TAG_STATUS, 1)
        startActivity(intent)
    }

    companion object {
        fun getInstance(KEY: Int): Fragment{
            val fragment = MovieFragment()
            fragment.arguments?.putInt(KEYS, KEY)

            return fragment
        }
    }
}
