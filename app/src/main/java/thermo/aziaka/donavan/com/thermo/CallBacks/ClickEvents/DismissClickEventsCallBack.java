package thermo.aziaka.donavan.com.thermo.CallBacks.ClickEvents;

import android.content.DialogInterface;

public class DismissClickEventsCallBack implements DialogInterface.OnClickListener {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }
}
