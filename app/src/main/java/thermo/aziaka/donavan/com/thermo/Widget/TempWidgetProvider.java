package thermo.aziaka.donavan.com.thermo.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import retrofit2.Call;
import thermo.aziaka.donavan.com.thermo.API.OpenWeatherMapAPI;
import thermo.aziaka.donavan.com.thermo.CallBacks.APICallBack.WidgetWeatherCallBack;
import thermo.aziaka.donavan.com.thermo.Constant;
import thermo.aziaka.donavan.com.thermo.Main.MainActivity;
import thermo.aziaka.donavan.com.thermo.Models.Weather;
import thermo.aziaka.donavan.com.thermo.R;
import thermo.aziaka.donavan.com.thermo.Utils;

import static thermo.aziaka.donavan.com.thermo.Constant.APP_ID;

public class TempWidgetProvider extends AppWidgetProvider {

    private RemoteViews views;
    private int[] idsWidgets;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        views = new RemoteViews(context.getPackageName(), R.layout.temp_widget);
        idsWidgets = Utils.getWidgetListFromDatabase(context);
        callWeatherAPIFromWidget(Utils.getWeatherListFromDatabase(context).get(0), context);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        idsWidgets = appWidgetIds;
        for (int appWidgetId : appWidgetIds) {
            views = new RemoteViews(context.getPackageName(), R.layout.temp_widget);
            widgetHandleActivity(context);
            widgetHandleUpdate(context);
            callWeatherAPIFromWidget(Utils.getWeatherListFromDatabase(context).get(0), context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        Utils.saveWidgetIds(appWidgetIds, context);
    }

    private void widgetHandleActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);
    }

    private void widgetHandleUpdate(Context context) {
        Intent intent = new Intent(context, TempWidgetProvider.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.update_temp, pendingIntent);
    }


    public void updateWidget(Weather item) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(0);
        String temp = String.format("%s°C", formatter.format(item.getMain().getTemp()));

        views.setTextViewText(R.id.temperature, temp);
        views.setTextViewText(R.id.city, item.getName());
        views.setTextViewText(R.id.country, item.getSys().getCountry());
        Picasso.get().load(Constant.URL_IMG + item.getWeather().get(0).getIcon() + ".png").into(views, R.id.favori, idsWidgets);
    }

    public void callWeatherAPIFromWidget(String city, Context context) {
        OpenWeatherMapAPI api = OpenWeatherMapAPI.retrofit.create(OpenWeatherMapAPI.class);
        Log.e("It doesn't works ?", "Current string " + city);
        Call<Weather> call = api.getWeatherWidget(city,"metric", "fr", APP_ID);
        call.enqueue(new WidgetWeatherCallBack(context, this));
    }

}
