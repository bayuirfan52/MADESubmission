package com.bayuirfan.madesubmission.features.dashboard


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.adapter.MovieRecyclerAdapter
import com.bayuirfan.madesubmission.features.details.DetailMovieActivity
import com.bayuirfan.madesubmission.model.Constant
import com.bayuirfan.madesubmission.model.data.MovieModel

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    private val adapter: MovieRecyclerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_movie)
        recyclerView.layoutManager = LinearLayoutManager(context)
        //        adapter = new MovieRecyclerAdapter(getActivity(), );
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    private fun goToDetails(movieModel: MovieModel) {
        val intent = Intent(activity, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_DETAIL, movieModel)
        intent.putExtra(Constant.TAG_STATUS, 1)
        startActivity(intent)
    }
}
