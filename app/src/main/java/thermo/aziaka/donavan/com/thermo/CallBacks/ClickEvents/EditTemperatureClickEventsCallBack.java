package thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents;

import android.content.DialogInterface;
import android.text.TextUtils;

import thermo.aziaka.donavan.com.thermo.Main.MainContract;
import thermo.aziaka.donavan.com.thermo.Models.City;

public class EditTemperatureClickEventsCallBack implements DialogInterface.OnClickListener {

    private MainContract.Presenter mPresenter;
    private int position;

    public EditTemperatureClickEventsCallBack(MainContract.Presenter presenter, int pos) {
        mPresenter = presenter;
        position = pos;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        City info = mPresenter.getUserCity();
        if (!TextUtils.isEmpty(info.getCity())){
            mPresenter.callWeatherAPI(info.getCity(), position);
        }
    }
}
