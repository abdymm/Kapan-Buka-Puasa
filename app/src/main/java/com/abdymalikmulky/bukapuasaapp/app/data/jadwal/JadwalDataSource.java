package com.abdymalikmulky.bukapuasaapp.app.data.jadwal;

import java.util.List;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/7/17.
 */

public interface JadwalDataSource {

    interface LoadJadwalByCityCallback {
        void onLoaded(List<Jadwal> jadwalList);
        void onNoData();
        void onFailed(String msg);
    }

    interface GetJadwalTodayByCityCallback {
        void onGet(Jadwal jadwal);
        void onNoData();
        void onFailed(String msg);
    }

    void loadByCity(int cityId, LoadJadwalByCityCallback callback);

    void getJadwalTodayByCity(int cityId, GetJadwalTodayByCityCallback callback);
}
