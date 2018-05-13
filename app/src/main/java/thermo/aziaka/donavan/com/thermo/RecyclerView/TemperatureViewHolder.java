package thermo.aziaka.donavan.com.thermo.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import thermo.aziaka.donavan.com.thermo.Models.Weather;
import thermo.aziaka.donavan.com.thermo.R;

public class TemperatureViewHolder extends RecyclerView.ViewHolder {

    private TextView temperature;
    private TextView city;
    private TextView country;

    public TemperatureViewHolder(View itemView) {
        super(itemView);
        temperature = itemView.findViewById(R.id.temperature);
        city = itemView.findViewById(R.id.city);
        country = itemView.findViewById(R.id.country);
    }

    public void bind(Weather item) {
        temperature.setText(String.format("%s°C", String.valueOf(item.getMain().getTemp())));
        city.setText(String.format("%s", String.valueOf(item.getName())));
        country.setText(String.format("%s", String.valueOf(item.getSys().getCountry())));
    }
}
