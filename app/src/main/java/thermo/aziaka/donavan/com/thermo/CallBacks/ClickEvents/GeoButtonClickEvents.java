package thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents;

import thermo.aziaka.donavan.com.thermo.Main.MainContract;

public class GeoButtonClickEvents implements ClickEventsCallBack {
    @Override
    public void handleResponse(MainContract.Presenter mPresenter) {
        mPresenter.callGeolocalisation();
    }
}
