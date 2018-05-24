package thermo.aziaka.donavan.com.thermo.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import thermo.aziaka.donavan.com.thermo.Main.MainActivity;
import thermo.aziaka.donavan.com.thermo.R;

public class TempWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.temp_widget);
            views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }


}
