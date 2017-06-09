package com.abdymalikmulky.bukapuasaapp.app.ui.splash;


import com.abdymalikmulky.bukapuasaapp.app.BasePresenter;
import com.abdymalikmulky.bukapuasaapp.app.BaseView;

public interface SplashContract {

    interface View extends BaseView<Presenter> {

        void showCityLoaded();

        void showLocationSetupDone();

        void showMain(int cityId);

        void showError(String msg);

    }

    interface Presenter extends BasePresenter {

        void fetchCity();

        void fetchJadwal();

        void setupLocation(String setupLocation);

    }
}