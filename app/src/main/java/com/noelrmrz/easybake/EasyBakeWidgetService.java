package com.noelrmrz.easybake;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;
import com.noelrmrz.easybake.POJO.Ingredient;
import com.noelrmrz.easybake.POJO.Recipe;
import com.noelrmrz.easybake.utilities.GsonClient;

import java.util.List;

public class EasyBakeWidgetService extends IntentService {

    private final String mSharedPrefFile = "com.noelrmrz.easybake";

    public EasyBakeWidgetService() {
        super("EasyBakeWidgetService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Util.SDK_INT >= 26) {
            String CHANNEL_ID = "my channel 01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "EasyBake Service", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("")
                    .build();

            startForeground(1, notification);
        }
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
                handleActionOpenRecipe();
            }
        }
    }

    private void handleActionOpenRecipe() {
        SharedPreferences sharedPreferences = getSharedPreferences(mSharedPrefFile, MODE_PRIVATE);
        String jsonRecipe = sharedPreferences.getString("jsonRecipe", "");
        StringBuilder stringBuilder = new StringBuilder();
        Recipe recipe = GsonClient.getGsonClient().fromJson(jsonRecipe, Recipe.class);
        List<Ingredient> ingredients = recipe.getmIngredients();
        for (Ingredient ingredient: ingredients) {
            String quantity = String.valueOf(ingredient.getmQuantity());
            String measure = ingredient.getmMeasure();
            String line = quantity + " " + measure + " " + ingredient.getmName();
            stringBuilder.append(line + "\n");
        }
        String ingredientString = stringBuilder.toString();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, EasyBakeWidgetProvider.class));
        EasyBakeWidgetProvider.updateWidgetRecipe(this,appWidgetManager,appWidgetIds, ingredientString, recipe.getmName(), jsonRecipe);
    }

    public static void startActionOpenRecipe(Context context) {
        Intent intent = new Intent(context, EasyBakeWidgetService.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        context.startService(intent);
    }
}
