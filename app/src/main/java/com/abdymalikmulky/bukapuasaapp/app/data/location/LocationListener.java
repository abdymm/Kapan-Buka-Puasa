package com.abdymalikmulky.bukapuasaapp.app.data.location;

import android.location.Address;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/9/17.
 */

public interface LocationListener {
    void onSuccessLoadLocation(Address address);
    void onFailedLoadLocation(String msg);
}
