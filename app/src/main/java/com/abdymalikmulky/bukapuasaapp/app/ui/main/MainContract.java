package com.abdymalikmulky.bukapuasaapp.app.ui.main;

import com.abdymalikmulky.bukapuasaapp.app.BasePresenter;
import com.abdymalikmulky.bukapuasaapp.app.BaseView;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.Jadwal;

import java.util.List;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/7/17.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {

        void showJadwal(List<Jadwal> jadwalList);

        void showJadwalToday(Jadwal jadwalToday);

        void showNoData();

        void showError(String msg);

    }
    interface Presenter extends BasePresenter {

        void loadJadwal(int cityId);

        void getNowJadwal(int cityId);
    }
}
