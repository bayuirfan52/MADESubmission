package com.bayuirfan.madesubmission.model.local

import android.os.AsyncTask
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bayuirfan.madesubmission.utils.LoadDataCallback
import java.lang.ref.WeakReference

class LoadTvShowAsync(tvShowOpenHelper: TvShowOpenHelper, loadDataCallback: LoadDataCallback<TvShowModel>)
    : AsyncTask<Void, Void, ArrayList<TvShowModel>>(){

    private val weakTvShowOpenHelper = WeakReference(tvShowOpenHelper)
    private val weakCallback = WeakReference(loadDataCallback)

    override fun onPreExecute() {
        super.onPreExecute()
        weakCallback.get()?.onPreExecute()
    }

    override fun doInBackground(vararg params: Void?): ArrayList<TvShowModel>? =
            weakTvShowOpenHelper.get()?.getAllTvShow()

    override fun onPostExecute(result: ArrayList<TvShowModel>) {
        super.onPostExecute(result)
        weakCallback.get()?.onPostExecute(result)
    }
}