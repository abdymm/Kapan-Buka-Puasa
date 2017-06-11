package com.abdymalikmulky.bukapuasaapp.app.ui.splash;

import com.abdymalikmulky.bukapuasaapp.app.data.city.City;
import com.abdymalikmulky.bukapuasaapp.app.data.city.CityDataSource;
import com.abdymalikmulky.bukapuasaapp.app.data.city.CityRepo;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.Jadwal;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalDataSource;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalRepo;

import java.util.List;

import timber.log.Timber;

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View splashView;
    private JadwalRepo jadwalRepo;
    private CityRepo cityRepo;

    public SplashPresenter(CityRepo cityRepo, JadwalRepo jadwalRepo, SplashContract.View splashView) {
        this.cityRepo = cityRepo;
        this.jadwalRepo = jadwalRepo;
        this.splashView = splashView;
        this.splashView.setPresenter(this);
    }

    @Override
    public void start() {
       fetchCity();
    }

    @Override
    public void stop() {
    }


    @Override
    public void fetchCity() {
        cityRepo.load(new CityDataSource.LoadCityCallback() {
            @Override
            public void onLoaded(List<City> cities) {
                splashView.showCityLoaded();
            }

            @Override
            public void onNoData() {
                splashView.showError("No Data");
            }

            @Override
            public void onFailed(String msg) {
                splashView.showError(msg);
            }
        });
    }

    @Override
    public void fetchJadwal() {
        cityRepo.getCurrentCity(new CityDataSource.GetCityCallback() {
            @Override
            public void onGet(final City city) {
                Timber.d("DataCity : %s", city.toString());
                jadwalRepo.loadByCity(city.getId(), new JadwalDataSource.LoadJadwalByCityCallback() {
                    @Override
                    public void onLoaded(List<Jadwal> jadwalList) {
                        Timber.d("DataJadwal : %s", jadwalList.toString());
                        splashView.showMain(city.getId());
                    }

                    @Override
                    public void onNoData() {
                        splashView.showError("No Data");
                    }

                    @Override
                    public void onFailed(String msg) {
                        splashView.showError(msg);
                    }
                });
            }

            @Override
            public void onFailed(String msg) {
                 splashView.showError(msg);

            }
        });
    }

    @Override
    public void setupLocation(String cityName) {
        Timber.d("DataCitySP");

        cityRepo.setCurrentCityLocation(cityName);
        splashView.showLocationSetupDone();
    }
}