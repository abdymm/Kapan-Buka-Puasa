package com.abdymalikmulky.bukapuasaapp.app.ui.splash;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abdymalikmulky.bukapuasaapp.BuildConfig;
import com.abdymalikmulky.bukapuasaapp.R;
import com.abdymalikmulky.bukapuasaapp.app.data.city.CityLocal;
import com.abdymalikmulky.bukapuasaapp.app.data.city.CityRemote;
import com.abdymalikmulky.bukapuasaapp.app.data.city.CityRepo;
import com.abdymalikmulky.bukapuasaapp.app.data.city.CitySp;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalLocal;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalRemote;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalRepo;
import com.abdymalikmulky.bukapuasaapp.app.data.location.LocationHelper;
import com.abdymalikmulky.bukapuasaapp.app.data.location.LocationListener;
import com.abdymalikmulky.bukapuasaapp.app.ui.main.MainActivity;
import com.abdymalikmulky.bukapuasaapp.util.ConstantsUtil;

import timber.log.Timber;

public class SplashActivity extends AppCompatActivity implements SplashContract.View, LocationListener {


    private SplashContract.Presenter splashPresenter;

    private JadwalRepo jadwalRepo;
    private CityRepo cityRepo;

    LocationHelper locationHelper;

    private TextView tvErrorMsg;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        tvErrorMsg = (TextView) findViewById(R.id.tv_error_msg);
        pbLoading = (ProgressBar) findViewById(R.id.pb_splash);

        jadwalRepo = new JadwalRepo(new JadwalLocal(), new JadwalRemote());
        cityRepo = new CityRepo(new CityLocal(new CitySp(getApplicationContext())), new CityRemote());

        splashPresenter = new SplashPresenter(cityRepo, jadwalRepo, this);
        locationHelper = new LocationHelper(this, this);
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
    public void showCityLoaded() {
        if (!locationHelper.checkPermissions()) {
            locationHelper.requestPermissions();
        } else {
            locationHelper.getAddress();
        }
    }

    @Override
    public void showLocationSetupDone() {
        splashPresenter.fetchJadwal();
    }

    @Override
    public void showMain(int cityId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ConstantsUtil.INTENT_EXTRA_CITY_ID, cityId);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        tvErrorMsg.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
    }



    //LOCATION MANAGER
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == locationHelper.REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Timber.d("fail3User interaction was cancelled");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Timber.d("Success granted permission");
            } else {
                // Build intent that displays the App settings screen.
                locationHelper.showSnackbar(R.string.setting_permission, R.string.label_setting, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",
                                BuildConfig.APPLICATION_ID, null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });


            }
        }
    }

    @Override
    public void onSuccessLoadLocation(Address address) {
        Timber.d("DataCitySP1 %s", address.toString());
        String cityName = address.getSubAdminArea();
        splashPresenter.setupLocation(cityName);
    }

    @Override
    public void onFailedLoadLocation(String msg) {
        Timber.d("DataCitySP2");

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        splashPresenter.setupLocation("");
    }
}
