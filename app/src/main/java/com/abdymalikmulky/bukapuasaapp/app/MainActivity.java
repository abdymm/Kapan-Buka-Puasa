package com.abdymalikmulky.bukapuasaapp.app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.abdymalikmulky.bukapuasaapp.R;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.Jadwal;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalRepo;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.local.JadwalLocal;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.remote.JadwalRemote;
import com.abdymalikmulky.bukapuasaapp.util.DateTimeUtil;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;
import timber.log.Timber;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity implements CountdownView.OnCountdownEndListener, MainContract.View{

    private static final int CITY_DUMMY = 14;

    private MainContract.Presenter mainPresenter;

    private JadwalRemote jadwalRemote;
    private JadwalLocal jadwalLocal;
    private JadwalRepo jadwalRepo;


    private Toolbar toolbar;
    private TextView tvDateMasehi, tvDateHijr, tvLabelMaghrib, tvTimeMaghrib;

    private DateTimeUtil dateTimeUtil;

    private String dateMaghrib = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        dateTimeUtil = new DateTimeUtil();

        setFindViewById();

        initRepoPresenter();


    }

    private void setCountdown(long timeBukaPuasa){

        CountdownView mCvCountdownViewHour = (CountdownView)findViewById(R.id.cv_countdownViewHour);
        CountdownView mCvCountdownViewSecond = (CountdownView)findViewById(R.id.cv_countdownViewSecond);

        mCvCountdownViewHour.setTag("CDHour");
        mCvCountdownViewSecond.setTag("CDSecond");


        mCvCountdownViewHour.start(timeBukaPuasa);
        mCvCountdownViewSecond.start(timeBukaPuasa);

        DynamicConfig.Builder dynamicConfigBuilder;

        dynamicConfigBuilder = new DynamicConfig.Builder();
        dynamicConfigBuilder
                .setShowDay(false)
                .setShowHour(true)
                .setShowMinute(true)
                .setShowSecond(false)
                .setShowMillisecond(false);

        mCvCountdownViewHour.dynamicShow(dynamicConfigBuilder.build());
    }
    private void setFindViewById(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvDateHijr = (TextView) findViewById(R.id.tv_date_hijr);
        tvDateMasehi = (TextView) findViewById(R.id.tv_date_masehi);
        tvLabelMaghrib = (TextView) findViewById(R.id.tv_maghrib_label);
        tvTimeMaghrib = (TextView) findViewById(R.id.tv_maghrib_time);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setFont();
    }
    private void setFont(){
        Typeface tf =Typeface.createFromAsset(getAssets(),"font/bubbler_one.ttf");

        tvDateHijr.setTypeface(tf);
        tvDateMasehi.setTypeface(tf);
        tvLabelMaghrib.setTypeface(tf);
        tvTimeMaghrib.setTypeface(tf);
    }
    private void initRepoPresenter(){
        jadwalLocal = new JadwalLocal();
        jadwalRemote = new JadwalRemote();
        jadwalRepo = new JadwalRepo(jadwalLocal, jadwalRemote);

        mainPresenter = new MainPresenter(this, jadwalRepo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                Toast.makeText(this, "Synced", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onEnd(CountdownView cv) {
        Object tag = cv.getTag();
        if (null != tag) {
            Log.d("Time-", "tag = " + tag.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.loadJadwal(CITY_DUMMY);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mainPresenter = presenter;
    }

    //TODO: pindahin ini ke splash screen
    @Override
    public void showJadwal(List<Jadwal> jadwalList) {
        Timber.d("Data-Jadwal %s", jadwalList.toString());
        int lastIndex = jadwalList.size()-1;

        //TODO: ini nanti di splash aja, trus tambahin check if exist jadwalnya
        mainPresenter.getNowJadwal(CITY_DUMMY);
    }

    @Override
    public void showJadwalToday(Jadwal jadwalToday) {
        String date = jadwalToday.getJadwalDate();
        String maghrib = jadwalToday.getMaghrib();
        dateMaghrib = date+" "+maghrib;

        long timeBukaPuasa = dateTimeUtil.getTimeDiff(dateMaghrib);
        setCountdown(timeBukaPuasa);

        tvDateMasehi.setText(date);
        tvTimeMaghrib.setText(DateTimeUtil.removeSecondInStringTime(maghrib));
    }

    @Override
    public void showNoData() {
        Timber.d("Data-Jadwal %s", "NODATA");

    }

    @Override
    public void showError(String msg) {
        Timber.d("Data-Jadwal %s", msg);

    }
}

