package com.bayuirfan.myfavorite.features


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
    }
}
