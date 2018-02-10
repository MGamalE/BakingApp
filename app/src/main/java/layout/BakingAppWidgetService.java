package layout;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.bakingapp.bakingapp.model.Recipe;

/**
 * Created by Mohammad on 05/02/2018.
 */

public class BakingAppWidgetService extends IntentService {
    public static final String RECIPE_ACTION_UPDATE = "app.com.bakingapp.bakingapp.action.update";


    public BakingAppWidgetService() {
        super("BakingAppWidgetService");
        setIntentRedelivery(true);

    }

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, BakingAppWidgetService.class);
        intent.setAction(RECIPE_ACTION_UPDATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (RECIPE_ACTION_UPDATE.equals(action)) {
                handleActionUpdateWidget((Recipe) intent.getParcelableExtra("recipe"));
            }

        }
    }


    private void handleActionUpdateWidget(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
        BakingAppWidget.updateRecipeWidget(this, appWidgetManager, appWidgetIds);

    }
}
