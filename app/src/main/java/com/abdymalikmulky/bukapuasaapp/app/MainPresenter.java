package com.abdymalikmulky.bukapuasaapp.app;

import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.Jadwal;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalDataSource;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalRepo;

import java.util.List;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/7/17.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mainView;

    private JadwalRepo jadwalRepo;

    public MainPresenter(MainContract.View mainView, JadwalRepo jadwalRepo) {
        this.mainView = mainView;
        this.jadwalRepo = jadwalRepo;

        this.mainView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loadJadwal(int cityId) {
        jadwalRepo.loadByCity(cityId, new JadwalDataSource.LoadJadwalByCityCallback() {
            @Override
            public void onLoaded(List<Jadwal> jadwalList) {
                mainView.showJadwal(jadwalList);
            }

            @Override
            public void onNoData() {
                mainView.showNoData();
            }

            @Override
            public void onFailed(String msg) {
                mainView.showError(msg);
            }
        });
    }

    @Override
    public void getNowJadwal(int cityId) {
        jadwalRepo.getJadwalTodayByCity(cityId, new JadwalDataSource.GetJadwalTodayByCityCallback() {
            @Override
            public void onGet(Jadwal jadwal) {
                mainView.showJadwalToday(jadwal);
            }

            @Override
            public void onNoData() {
                mainView.showNoData();
            }

            @Override
            public void onFailed(String msg) {
                mainView.showError(msg);
            }
        });
    }


}
