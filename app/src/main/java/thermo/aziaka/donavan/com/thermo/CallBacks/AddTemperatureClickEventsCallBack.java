package thermo.aziaka.donavan.com.thermo.CallBacks;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;

import thermo.aziaka.donavan.com.thermo.Main.MainContract;
import thermo.aziaka.donavan.com.thermo.Models.City;

public class AddTemperatureClickEventsCallBack implements DialogInterface.OnClickListener {

    private MainContract.Presenter mPresenter;

    public AddTemperatureClickEventsCallBack(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        City info = mPresenter.getUserCity();
        if (!TextUtils.isEmpty(info.getCity())){
            Log.e("Datas", info.getCity());
            if (!TextUtils.isEmpty(info.getCountry())) {
               mPresenter.callWeatherAPI(info.getCity() + "," + info.getCountry());
            } else {
                mPresenter.callWeatherAPI(info.getCity());
            }
        }
    }


}
