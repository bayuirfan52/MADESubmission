package com.bayuirfan.madesubmission.features.widget

import android.appwidget.*
import android.content.*
import android.net.Uri
import android.widget.RemoteViews
import com.bayuirfan.madesubmission.R
import com.bayuirfan.madesubmission.services.StackWidgetService

/**
 * Implementation of App Widget functionality.
 */
class CatalogueWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
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
                return
            }
        }
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.catalogue_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)



            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        const val EXTRA_ITEM = "com.bayuirfan.madesubmission.EXTRA_ITEM"
        const val LIST_ACTION = "com.bayuirfan.madesubmission.LIST_ACTION"
    }
}

