package thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents;

import android.view.View;

import thermo.aziaka.donavan.com.thermo.Main.MainContract;

public class RefreshClickEventsCallBack implements View.OnClickListener {

    private thermo.aziaka.donavan.com.thermo.Main.MainContract.View mView;
    private int position;

    public RefreshClickEventsCallBack(int pos, MainContract.View view) {
        mView = view;
        position = pos;
    }

    @Override
    public void onClick(View v) {
        mView.refreshTemperature(position);
    }

}
