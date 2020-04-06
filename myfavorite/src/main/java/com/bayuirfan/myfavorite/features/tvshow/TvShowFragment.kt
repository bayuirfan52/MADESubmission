package com.bayuirfan.myfavorite.features.tvshow


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bayuirfan.myfavorite.R
import com.bayuirfan.myfavorite.adapter.TvShowRecyclerAdapter
import com.bayuirfan.myfavorite.model.TvShowModel
import kotlinx.android.synthetic.main.fragment_tv_show.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : androidx.fragment.app.Fragment() {
    private lateinit var adapter: TvShowRecyclerAdapter
    private val tvShowList = mutableListOf<TvShowModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            adapter = TvShowRecyclerAdapter(it, tvShowList as ArrayList<TvShowModel>)
            rv_tv_show.layoutManager = LinearLayoutManager(it)
        }

        rv_tv_show.adapter = adapter
        rv_tv_show.setHasFixedSize(true)
        loadAllData()
    }

    private fun loadAllData(){
        val viewModel = ViewModelProvider(this).get(TvShowViewModel::class.java)
        viewModel.getAllData(this.context).observe(viewLifecycleOwner, observer)
    }

    private val observer : Observer<ArrayList<TvShowModel>> = Observer {data ->
        if (data != null){
            if (data.size == 0){
                showError()
            } else {
                hideError()
                tvShowList.clear()
                tvShowList.addAll(data)
                adapter.notifyDataSetChanged()
            }
        } else {
            showError()
        }
    }

    private fun showError(){
        iv_not_found_favorite.visibility = View.VISIBLE
        tv_not_found_favorite.visibility = View.VISIBLE
        rv_tv_show.visibility = View.GONE
    }

    private fun hideError(){
        iv_not_found_favorite.visibility = View.GONE
        tv_not_found_favorite.visibility = View.GONE
        rv_tv_show.visibility = View.VISIBLE
    }
}
