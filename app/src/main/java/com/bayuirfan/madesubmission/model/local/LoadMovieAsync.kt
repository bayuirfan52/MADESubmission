package com.bayuirfan.madesubmission.model.local

import android.os.AsyncTask
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.utils.LoadDataCallback
import java.lang.ref.WeakReference

class LoadMovieAsync(movieOpenHelper: MovieOpenHelper, loadDataCallback: LoadDataCallback<MovieModel>): AsyncTask<Void, Void, ArrayList<MovieModel>>() {
    private val weakMovieHelper = WeakReference(movieOpenHelper)
    private val weakCallback = WeakReference(loadDataCallback)

    override fun onPreExecute() {
        super.onPreExecute()
        weakCallback.get()?.onPreExecute()
    }
    override fun doInBackground(vararg params: Void?): ArrayList<MovieModel>? =
        weakMovieHelper.get()?.getAllMovies()

    override fun onPostExecute(result: ArrayList<MovieModel>) {
        super.onPostExecute(result)
        weakCallback.get()?.onPostExecute(result)
    }
}