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
import com.bayuirfan.madesubmission.adapter.TvShowRecyclerAdapter
import com.bayuirfan.madesubmission.features.details.DetailMovieActivity
import com.bayuirfan.madesubmission.model.Constant
import com.bayuirfan.madesubmission.model.data.TvShowModel

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private val adapter: TvShowRecyclerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_tv_show)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        //        adapter = new TvShowRecyclerAdapter(getContext());
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    private fun goToDetails(tvShowModel: TvShowModel) {
        val intent = Intent(activity, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.EXTRA_DETAIL, tvShowModel)
        intent.putExtra(Constant.TAG_STATUS, 2)
        startActivity(intent)
    }
}
