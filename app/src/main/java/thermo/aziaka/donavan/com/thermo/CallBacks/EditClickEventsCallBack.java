package thermo.aziaka.donavan.com.thermo.CallBacks;

import android.content.DialogInterface;
import android.view.View;

import thermo.aziaka.donavan.com.thermo.Main.MainContract;

public class EditClickEventsCallBack implements View.OnClickListener {

    private thermo.aziaka.donavan.com.thermo.Main.MainContract.View mView;
    private int position;

    public EditClickEventsCallBack(int pos, MainContract.View view) {
        mView = view;
        position = pos;
    }

    @Override
    public void onClick(View v) {
        mView.editTemperatureItem(position);
    }
}
