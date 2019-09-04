package com.bayuirfan.myfavorite.features.movie


import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.bayuirfan.myfavorite.R
import com.bayuirfan.myfavorite.adapter.MovieRecyclerAdapter
import com.bayuirfan.myfavorite.model.MovieModel
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {
    private lateinit var adapter: MovieRecyclerAdapter
    private val list = mutableListOf<MovieModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            adapter = MovieRecyclerAdapter(it, list as ArrayList<MovieModel>)
            rv_movie.layoutManager = LinearLayoutManager(it)
        }

        rv_movie.adapter = adapter
        rv_movie.setHasFixedSize(true)
        loadAllData()
    }

    private fun loadAllData(){
        val viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.getAllData(this.context).observe(this, observer)
    }

    private val observer : Observer<ArrayList<MovieModel>> = Observer {data ->
        if (data != null){
            if (data.size == 0){
                showError()
            } else {
                hideError()
                list.clear()
                list.addAll(data)
                adapter.notifyDataSetChanged()
            }
        } else {
            showError()
        }
    }

    private fun showError(){
        iv_not_found_favorite.visibility = View.VISIBLE
        tv_not_found_favorite.visibility = View.VISIBLE
        rv_movie.visibility = View.GONE
    }

    private fun hideError(){
        iv_not_found_favorite.visibility = View.GONE
        tv_not_found_favorite.visibility = View.GONE
        rv_movie.visibility = View.VISIBLE
    }
}
