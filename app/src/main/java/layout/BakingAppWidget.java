package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.bakingapp.bakingapp.activity.MainActivity;
import com.bakingapp.bakingapp.model.Ingredient;
import com.bakingapp.bakingapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        String allIngredients = "";
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences("ingredientPref", Context.MODE_PRIVATE);

        String ingre = sharedPreferences.getString("ingredients", "");
        Type list = new TypeToken<ArrayList<Ingredient>>() {
        }.getType();

        ArrayList<Ingredient> ingredients = gson.fromJson(ingre, list);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        view.setOnClickPendingIntent(R.id.list_ingredient_widget, appPendingIntent);

        for (int i = 0; i < ingredients.size(); i++) {
            allIngredients += " " + ingredients.get(i).getIngredient() + " ** " + ingredients.get(i).getQuantity() + " ** " + ingredients.get(i).getMeasure() + "\n \n";
        }

        if (allIngredients != null) {
            view.setTextViewText(R.id.list_ingredient_widget, allIngredients);
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, view);

    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingAppWidgetService.startActionUpdateWidget(context);
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

