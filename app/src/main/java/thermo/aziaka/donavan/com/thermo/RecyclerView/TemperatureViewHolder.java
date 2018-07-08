package thermo.aziaka.donavan.com.thermo.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import thermo.aziaka.donavan.com.thermo.CallBacks.DeleteClickEventsCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.EditClickEventsCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.FavoriClickEventsCallBack;
import thermo.aziaka.donavan.com.thermo.CallBacks.RefreshClickEventsCallBack;
import thermo.aziaka.donavan.com.thermo.Constant;
import thermo.aziaka.donavan.com.thermo.Main.MainContract;
import thermo.aziaka.donavan.com.thermo.Models.Weather;
import thermo.aziaka.donavan.com.thermo.R;

public class TemperatureViewHolder extends RecyclerView.ViewHolder {

    private TextView temperature;
    private TextView city;
    private TextView country;
    private ImageButton refresh;
    private ImageView favori;
    private TextView description;

    public TemperatureViewHolder(View itemView) {
        super(itemView);
        temperature = itemView.findViewById(R.id.temperature);
        city = itemView.findViewById(R.id.city);
        country = itemView.findViewById(R.id.country);
        refresh = itemView.findViewById(R.id.delete_temp);
        favori = itemView.findViewById(R.id.favori);
        description = itemView.findViewById(R.id.state);
    }

    public void bind(Weather item) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMaximumFractionDigits(0);
        temperature.setText(String.format("%s°C", formatter.format(item.getMain().getTemp())));
        city.setText(String.format("%s", String.valueOf(item.getName())));
        country.setText(String.format("%s", String.valueOf(item.getSys().getCountry())));
        description.setText(String.format("%s", item.getWeather().get(item.getWeather().size() - 1).getDescription()));
        Picasso.get().load(Constant.URL_IMG + item.getWeather().get(item.getWeather().size() - 1).getIcon() + ".png").into(favori);
    }

    public void events(final int position, MainContract.View mView) {
        refresh.setOnClickListener(new RefreshClickEventsCallBack(position, mView));
        favori.setOnClickListener(new FavoriClickEventsCallBack(position, mView));
        itemView.setOnClickListener(new EditClickEventsCallBack(position, mView));
    }
}
