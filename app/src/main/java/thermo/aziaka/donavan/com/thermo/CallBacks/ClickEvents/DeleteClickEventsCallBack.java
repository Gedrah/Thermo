package thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents;

import android.content.DialogInterface;
import android.view.View;

import thermo.aziaka.donavan.com.thermo.Main.MainContract;

public class DeleteClickEventsCallBack implements  View.OnClickListener {

    private MainContract.View mView;
    private int position;

    public DeleteClickEventsCallBack(int pos, MainContract.View view) {
        mView = view;
        position = pos;
    }

    @Override
    public void onClick(View v) {
        mView.deleteTemperatureItem(position);
    }
}
