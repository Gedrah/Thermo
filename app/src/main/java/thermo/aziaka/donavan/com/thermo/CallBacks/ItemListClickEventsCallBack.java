package thermo.aziaka.donavan.com.thermo.CallBacks;

import android.view.View;

import thermo.aziaka.donavan.com.thermo.Main.MainContract;

public class ItemListClickEventsCallBack implements View.OnClickListener {

    private MainContract.View mView;

    public ItemListClickEventsCallBack(MainContract.View view)  {
        mView = view;
    }

    @Override
    public void onClick(View v) {

    }
}
