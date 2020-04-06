package com.bayuirfan.madesubmission.features.widget

import android.app.PendingIntent
import android.appwidget.*
import android.content.*
import android.net.Uri
import android.widget.RemoteViews
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.features.details.tvshow.DetailTvShowActivity
import com.bayuirfan.madesubmission.model.data.TvShowModel
import com.bayuirfan.madesubmission.services.TvShowStackWidgetService
import com.bayuirfan.madesubmission.utils.Constant.BACKDROP_PATH
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_DETAIL
import com.bayuirfan.madesubmission.utils.Constant.EXTRA_ITEM
import com.bayuirfan.madesubmission.utils.Constant.FIRST_AIR_DATE
import com.bayuirfan.madesubmission.utils.Constant.ID
import com.bayuirfan.madesubmission.utils.Constant.ID_DATA
import com.bayuirfan.madesubmission.utils.Constant.LIST_ACTION
import com.bayuirfan.madesubmission.utils.Constant.NAME
import com.bayuirfan.madesubmission.utils.Constant.OVERVIEW
import com.bayuirfan.madesubmission.utils.Constant.POSTER_PATH
import com.bayuirfan.madesubmission.utils.Constant.VOTE_AVERAGE

/**
 * Implementation of App Widget functionality.
 */
class TvShowCatalogueWidget : AppWidgetProvider() {

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
                val model = extras?.let { TvShowModel(
                        it.getInt(ID),
                        it.getInt(ID_DATA),
                        it.getString(NAME),
                        it.getString(POSTER_PATH),
                        it.getString(BACKDROP_PATH),
                        it.getString(OVERVIEW),
                        it.getString(VOTE_AVERAGE),
                        it.getString(FIRST_AIR_DATE)
                )}

                val moveToDetail = Intent(context, DetailTvShowActivity::class.java)
                moveToDetail.putExtra(EXTRA_DETAIL, model)
                moveToDetail.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context?.startActivity(moveToDetail)
            }
        }
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {
            val intent = Intent(context, TvShowStackWidgetService::class.java)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

            val views = RemoteViews(context.packageName, R.layout.tv_show_catalogue_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            val stackIntent = Intent(context, TvShowCatalogueWidget::class.java)
            stackIntent.action = LIST_ACTION
            stackIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val pendingIntent = PendingIntent.getBroadcast(context, 0, stackIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            views.setPendingIntentTemplate(R.id.stack_view, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

