package com.bayuirfan.madesubmission.features.dashboard.tvshow


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.adapter.TvShowRecyclerAdapter
import com.bayuirfan.madesubmission.features.details.tvshow.DetailTvShowActivity
import com.bayuirfan.madesubmission.model.data.Discover
import com.bayuirfan.madesubmission.model.data.TvShowModel
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
    }

    private fun getTvShowData(){
        showLoading(true)
        val tvShowViewModel = ViewModelProvider(this).get(TvShowViewModel::class.java)
        tvShowViewModel.getTvShowData().observe(viewLifecycleOwner, getTvShow)
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