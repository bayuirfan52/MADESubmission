package com.bayuirfan.madesubmission.features.dashboard.movie

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.adapter.MovieRecyclerAdapter
import com.bayuirfan.madesubmission.features.dashboard.search.SearchMovieActivity
import com.bayuirfan.madesubmission.features.details.movie.DetailMovieActivity
import com.bayuirfan.madesubmission.model.data.*
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_DETAIL
import com.bayuirfan.madesubmission.utils.OnItemClickCallback
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.view.*

class MovieFragment : Fragment(), OnItemClickCallback<MovieModel> {
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
            adapter = MovieRecyclerAdapter(it, movieModel as ArrayList<MovieModel>,this)
        }
        view.rv_movie.adapter = adapter
        view.rv_movie.setHasFixedSize(true)

        fab_search_movie.setOnClickListener {
            val intent = Intent(context, SearchMovieActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getMovieData(){
        val movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.getMovieList().observe(viewLifecycleOwner, getMovieList)
        showLoading(true)
    }

    private val getMovieList: Observer<Discover<MovieModel>> = Observer { data ->
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
        intent.putExtra(EXTRA_DETAIL, movieModel)
        startActivity(intent)
    }

    override fun onItemClicked(model: MovieModel) {
        goToDetails(model)
    }
}
