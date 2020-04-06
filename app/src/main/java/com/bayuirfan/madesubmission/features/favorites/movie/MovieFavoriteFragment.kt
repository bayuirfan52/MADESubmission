package com.bayuirfan.madesubmission.features.favorites.movie


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.adapter.MovieRecyclerAdapter
import com.bayuirfan.madesubmission.features.dashboard.movie.MovieViewModel
import com.bayuirfan.madesubmission.features.details.movie.DetailMovieActivity
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_DETAIL
import com.bayuirfan.madesubmission.utils.OnItemClickCallback
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import kotlinx.android.synthetic.main.fragment_movie_favorite.view.*

class MovieFavoriteFragment : Fragment(), OnItemClickCallback<MovieModel> {
    private lateinit var adapter: MovieRecyclerAdapter
    private val data = mutableListOf<MovieModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            adapter = MovieRecyclerAdapter(it, data as ArrayList<MovieModel>, this)
            view.rv_movie_favorite.layoutManager = LinearLayoutManager(it)
        }
        view.rv_movie_favorite.adapter = adapter
        view.rv_movie_favorite.setHasFixedSize(true)
    }

    private fun getFavoriteData(){
        showLoading(true)
        val movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.getMovieLocalList(this.context).observe(this,getMovieList)
    }

    private val getMovieList : Observer<ArrayList<MovieModel>> = Observer { data ->
        showLoading(false)
        if (data != null){
            if (data.size == 0){
                showError()
            } else {
                this.data.clear()
                this.data.addAll(data)
                adapter.notifyDataSetChanged()
                hideError()
            }
        } else {
            showError()
        }
    }

    override fun onResume() {
        super.onResume()
        getFavoriteData()
    }

    private fun showError(){
        iv_not_found_favorite.visibility = View.VISIBLE
        tv_not_found_favorite.visibility = View.VISIBLE
        rv_movie_favorite.visibility = View.GONE
    }

    private fun hideError(){
        iv_not_found_favorite.visibility = View.GONE
        tv_not_found_favorite.visibility = View.GONE
        rv_movie_favorite.visibility = View.VISIBLE
    }

    private fun goToDetails(tvShowModel: MovieModel) {
        val intent = Intent(activity, DetailMovieActivity::class.java)
        intent.putExtra(EXTRA_DETAIL, tvShowModel)
        startActivity(intent)
    }

    private fun showLoading(value: Boolean){
        if (value) {
            progress_movie_favorite.visibility = View.VISIBLE
            rv_movie_favorite.visibility = View.GONE
        } else {
            progress_movie_favorite.visibility = View.GONE
        }
    }

    override fun onItemClicked(model: MovieModel) {
        goToDetails(model)
    }
}
