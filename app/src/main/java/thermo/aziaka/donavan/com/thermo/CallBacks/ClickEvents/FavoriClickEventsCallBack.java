package thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents;

import android.view.View;

import thermo.aziaka.donavan.com.thermo.Main.MainContract;

public class FavoriClickEventsCallBack implements View.OnClickListener {

    private MainContract.View mView;
    private int position;

    public FavoriClickEventsCallBack(int pos, MainContract.View view) {
        mView = view;
        position = pos;
    }

    @Override
    public void onClick(View v) {
        mView.setFavoriItem(position);
        mView.updateMainTemperature(0);
    }
}
