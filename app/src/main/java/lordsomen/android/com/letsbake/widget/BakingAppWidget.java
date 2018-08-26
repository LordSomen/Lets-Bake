package lordsomen.android.com.letsbake.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import lordsomen.android.com.letsbake.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, RemoteViews views) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);

        //Create an Intent with the AppWidgetManager.ACTION_APPWIDGET_UPDATE action//

        Intent intentUpdate = new Intent(context, BakingAppWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

//Update the current widget instance only, by creating an array that contains the widget’s unique ID//

        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

//Wrap the intent as a PendingIntent, using PendingIntent.getBroadcast()//

        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                context, appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);

//Send the pending intent in response to the user tapping the ‘Update’ TextView//

        views.setOnClickPendingIntent(R.id.main_item_widget_button, pendingUpdate);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, BakingAppWidget.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.baking_app_widget
            );
            Intent intent = new Intent(context, BakingWidgetRemoteViewsService.class);
            views.setRemoteAdapter(R.id.widget_ingredients_list, intent);
//            views.setTextViewText(R.id.widget_title_textView,);

            updateAppWidget(context, appWidgetManager, appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, BakingAppWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widget_ingredients_list);
        }
        super.onReceive(context, intent);
    }

}

