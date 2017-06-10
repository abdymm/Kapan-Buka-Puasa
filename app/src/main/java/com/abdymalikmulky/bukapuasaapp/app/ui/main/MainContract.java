package com.abdymalikmulky.bukapuasaapp.app.ui.main;

import com.abdymalikmulky.bukapuasaapp.app.BasePresenter;
import com.abdymalikmulky.bukapuasaapp.app.BaseView;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.Jadwal;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/7/17.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {


        void showJadwalToday(Jadwal jadwalToday);

        void showNoData();

        void showError(String msg);

    }
    interface Presenter extends BasePresenter {

        void getNowJadwal(int cityId);
    }
}
