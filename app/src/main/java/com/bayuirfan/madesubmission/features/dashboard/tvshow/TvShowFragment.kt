package com.bayuirfan.madesubmission.features.dashboard.tvshow


import android.arch.lifecycle.*
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.adapter.TvShowRecyclerAdapter
import com.bayuirfan.madesubmission.features.dashboard.search.SearchTvShowActivity
import com.bayuirfan.madesubmission.features.details.tvshow.DetailTvShowActivity
import com.bayuirfan.madesubmission.model.data.*
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_DETAIL
import com.bayuirfan.madesubmission.utils.OnItemClickCallback
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.android.synthetic.main.fragment_tv_show.view.*

class TvShowFragment : Fragment(), OnItemClickCallback<TvShowModel> {
    private lateinit var adapter: TvShowRecyclerAdapter
    private var data = mutableListOf<TvShowModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTvShowData()
        view.rv_tv_show.layoutManager = LinearLayoutManager(activity)
        context?.let {
            adapter = TvShowRecyclerAdapter(it, data as ArrayList<TvShowModel>, this)
        }
        view.rv_tv_show.adapter = adapter
        view.rv_tv_show.setHasFixedSize(true)

        fab_search_tv_show.setOnClickListener {
            val intent = Intent(context, SearchTvShowActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getTvShowData(){
        showLoading(true)
        val tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        tvShowViewModel.getTvShowData().observe(this, getTvShow)
    }

    private val getTvShow: Observer<Discover<TvShowModel>> = Observer {data ->
        showLoading(false)
        if (data != null){
            if (data.status_code == null){
                this.data.clear()
                this.data.addAll(data.results)
                adapter.notifyDataSetChanged()
                hideError()
            } else {
                showError()
            }
        } else {
            showError()
        }
    }

    private fun showLoading(value: Boolean){
        if (value){
            progress_tv_show.visibility = View.VISIBLE
            rv_tv_show.visibility = View.GONE
        } else {
            progress_tv_show.visibility = View.GONE
        }
    }

    private fun showError(){
        iv_not_found.visibility = View.VISIBLE
        tv_not_found.visibility = View.VISIBLE
        rv_tv_show.visibility = View.GONE
    }

    private fun hideError(){
        iv_not_found.visibility = View.GONE
        tv_not_found.visibility = View.GONE
        rv_tv_show.visibility = View.VISIBLE
    }

    private fun goToDetails(tvShowModel: TvShowModel) {
        val intent = Intent(activity, DetailTvShowActivity::class.java)
        intent.putExtra(EXTRA_DETAIL, tvShowModel)
        startActivity(intent)
    }

    override fun onItemClicked(model: TvShowModel) {
        goToDetails(model)
    }
}