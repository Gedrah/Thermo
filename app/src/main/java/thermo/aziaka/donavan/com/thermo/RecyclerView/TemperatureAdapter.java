package thermo.aziaka.donavan.com.thermo.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;

import thermo.aziaka.donavan.com.thermo.Main.MainContract;
import thermo.aziaka.donavan.com.thermo.Models.Weather;
import thermo.aziaka.donavan.com.thermo.R;

public class TemperatureAdapter extends RecyclerSwipeAdapter<TemperatureViewHolder> {

    private List<Weather> currentList;
    private MainContract.View currentView;

    public TemperatureAdapter(MainContract.View mView, List<Weather> list) {
        currentList = list;
        currentView = mView;
    }

    @Override
    public TemperatureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((Context)currentView);
        View view = layoutInflater.inflate(R.layout.temp_item, parent, false);
        return new TemperatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TemperatureViewHolder holder, int position) {
        holder.bind(currentList.get(position));
        holder.events(position, currentView);
    }

    @Override
    public int getItemCount() {
        return currentList.size();
    }

    public void updateTemperatureList(List<Weather> list) {
        currentList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
