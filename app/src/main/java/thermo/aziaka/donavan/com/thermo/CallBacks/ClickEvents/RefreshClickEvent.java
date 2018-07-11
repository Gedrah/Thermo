package thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents;

import android.util.Log;

import thermo.aziaka.donavan.com.thermo.Main.MainContract;

public class RefreshClickEvent implements ClickEventsCallBack {
    @Override
    public void handleResponse(MainContract.Presenter mPresenter) {
        mPresenter.updateWeatherList();
    }
}
