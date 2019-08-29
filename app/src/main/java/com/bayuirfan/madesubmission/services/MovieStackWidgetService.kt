package com.bayuirfan.madesubmission.services

import android.content.Intent
import android.widget.RemoteViewsService

class MovieStackWidgetService: RemoteViewsService(){
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
            MovieStackWidgetRemoteFactory(this.applicationContext)

}
