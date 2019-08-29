package com.bayuirfan.madesubmission.services

import android.content.Intent
import android.widget.RemoteViewsService

class TvShowStackWidgetService: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
            TvShowStackWidgetRemoteFactory(this.applicationContext)
}