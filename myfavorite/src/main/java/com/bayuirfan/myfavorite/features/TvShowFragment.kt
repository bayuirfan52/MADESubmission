package com.bayuirfan.myfavorite.features


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.bayuirfan.myfavorite.R
import com.bayuirfan.myfavorite.adapter.TvShowRecyclerAdapter
import com.bayuirfan.myfavorite.model.TvShowModel
import kotlinx.android.synthetic.main.fragment_tv_show.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {
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
    }
}
