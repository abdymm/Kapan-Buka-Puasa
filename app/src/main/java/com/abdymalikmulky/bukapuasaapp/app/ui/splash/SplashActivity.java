package com.abdymalikmulky.bukapuasaapp.app.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.abdymalikmulky.bukapuasaapp.R;
import com.abdymalikmulky.bukapuasaapp.app.data.city.CityLocal;
import com.abdymalikmulky.bukapuasaapp.app.data.city.CityRemote;
import com.abdymalikmulky.bukapuasaapp.app.data.city.CityRepo;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalLocal;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalRemote;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalRepo;
import com.abdymalikmulky.bukapuasaapp.app.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashContract.Presenter splashPresenter;

    private JadwalRepo jadwalRepo;
    private CityRepo cityRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        jadwalRepo = new JadwalRepo(new JadwalLocal(), new JadwalRemote());
        cityRepo = new CityRepo(new CityLocal(), new CityRemote());

        splashPresenter = new SplashPresenter(cityRepo, jadwalRepo, this);

    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        splashPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashPresenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        splashPresenter.stop();
    }

    @Override
    public void showMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
