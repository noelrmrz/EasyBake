package com.noelrmrz.easybake;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class EasyBakeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String jsonIngredients, String recipeName, String jsonRecipe) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.easy_bake_widget);

        if (jsonIngredients.equals("")) {
            jsonIngredients = "No  ingredients to view";
        }

        views.setTextViewText(R.id.appwidget_text, jsonIngredients);
        views.setTextViewText(R.id.tv_widget_ingredients_label, recipeName + " ingredients");

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, jsonRecipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        EasyBakeWidgetService.startActionOpenRecipe(context);
    }

    public static void updateWidgetRecipe(Context context,
                                          AppWidgetManager appWidgetManager, int[] appWidgetIds,
                                          String ingredientString, String recipeName, String jsonRecipe) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ingredientString, recipeName, jsonRecipe);
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
}

