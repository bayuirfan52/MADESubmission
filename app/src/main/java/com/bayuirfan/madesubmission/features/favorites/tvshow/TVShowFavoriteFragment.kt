package com.bayuirfan.madesubmission.features.favorites.tvshow


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
import com.bayuirfan.madesubmission.adapter.TvShowRecyclerAdapter
import com.bayuirfan.madesubmission.features.dashboard.tvshow.TvShowViewModel
import com.bayuirfan.madesubmission.features.details.tvshow.DetailTvShowActivity
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_DETAIL
import com.bayuirfan.madesubmission.utils.OnItemClickCallback
import kotlinx.android.synthetic.main.fragment_tvshow_favorite.*
import kotlinx.android.synthetic.main.fragment_tvshow_favorite.view.*

class TVShowFavoriteFragment : Fragment(), OnItemClickCallback<TvShowModel> {
    private lateinit var adapter: TvShowRecyclerAdapter
    private val data = mutableListOf<TvShowModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            adapter = TvShowRecyclerAdapter(it, data as ArrayList<TvShowModel>, this)
            view.rv_tv_show_favorite.layoutManager = LinearLayoutManager(it)
        }
        view.rv_tv_show_favorite.adapter = adapter
        view.rv_tv_show_favorite.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        getFavoriteData()
    }

    private fun getFavoriteData(){
        showLoading(true)
        val tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        tvShowViewModel.getTvShowLocal(this.context).observe(this,getTvShowList)
    }

    private val getTvShowList: Observer<ArrayList<TvShowModel>> = Observer { data ->
        if (data !=  null){
            if (data.size > 0) {
                hideError()
                this.data.clear()
                this.data.addAll(data)
                adapter.notifyDataSetChanged()
            } else {
                showError()
            }
        } else {
            showError()
        }
        showLoading(false)
    }

    private fun showError(){
        iv_not_found_favorite.visibility = View.VISIBLE
        tv_not_found_favorite.visibility = View.VISIBLE
        rv_tv_show_favorite.visibility = View.GONE
    }

    private fun hideError(){
        iv_not_found_favorite.visibility = View.GONE
        tv_not_found_favorite.visibility = View.GONE
        rv_tv_show_favorite.visibility = View.VISIBLE
    }

    private fun goToDetails(tvShowModel: TvShowModel) {
        val intent = Intent(activity, DetailTvShowActivity::class.java)
        intent.putExtra(EXTRA_DETAIL, tvShowModel)
        startActivity(intent)
    }

    private fun showLoading(value: Boolean){
        if (value) {
            progress_tv_show_favorite.visibility = View.VISIBLE
            rv_tv_show_favorite.visibility = View.GONE
        } else {
            progress_tv_show_favorite.visibility = View.GONE
        }
    }

    override fun onItemClicked(model: TvShowModel) {
        goToDetails(model)
    }
}
