package com.bayuirfan.madesubmission.features.widget

import android.app.PendingIntent
import android.appwidget.*
import android.content.*
import android.net.Uri
import android.widget.RemoteViews
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.features.details.movie.DetailMovieActivity
import com.bayuirfan.madesubmission.model.data.MovieModel
import com.bayuirfan.madesubmission.services.MovieStackWidgetService
import com.bayuirfan.madesubmission.utils.Constant.BACKDROP_PATH
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_DETAIL
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_ITEM
import com.bayuirfan.madesubmission.utils.Constant.ID
import com.bayuirfan.madesubmission.utils.Constant.ID_DATA
import com.bayuirfan.madesubmission.utils.Constant.LIST_ACTION
import com.bayuirfan.madesubmission.utils.Constant.OVERVIEW
import com.bayuirfan.madesubmission.utils.Constant.POSTER_PATH
import com.bayuirfan.madesubmission.utils.Constant.RELEASE_DATE
import com.bayuirfan.madesubmission.utils.Constant.TITLE
import com.bayuirfan.madesubmission.utils.Constant.VOTE_AVERAGE

/**
 * Implementation of App Widget functionality.
 */
class MovieCatalogueWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action != null){
            if (intent.action == LIST_ACTION){
                val extras = intent.getBundleExtra(EXTRA_ITEM)
                val model = MovieModel(
                        extras.getInt(ID),
                        extras.getInt(ID_DATA),
                        extras.getString(TITLE),
                        extras.getString(POSTER_PATH),
                        extras.getString(BACKDROP_PATH),
                        extras.getString(OVERVIEW),
                        extras.getString(VOTE_AVERAGE),
                        extras.getString(RELEASE_DATE)
                )

                val moveToDetail = Intent(context, DetailMovieActivity::class.java)
                moveToDetail.putExtra(EXTRA_DETAIL, model)
                moveToDetail.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context?.startActivity(moveToDetail)
            }
        }
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {
            val intent = Intent(context, MovieStackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

            val views = RemoteViews(context.packageName, R.layout.movie_catalogue_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            val stackIntent = Intent(context, MovieCatalogueWidget::class.java)
            stackIntent.action = LIST_ACTION
            stackIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val pendingIntent = PendingIntent.getBroadcast(context, 0, stackIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            views.setPendingIntentTemplate(R.id.stack_view, pendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

