package com.abdymalikmulky.bukapuasaapp.app.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdymalikmulky.bukapuasaapp.R;
import com.abdymalikmulky.bukapuasaapp.app.data.city.CitySp;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.Jadwal;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalLocal;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalRemote;
import com.abdymalikmulky.bukapuasaapp.app.data.jadwal.JadwalRepo;
import com.abdymalikmulky.bukapuasaapp.util.ConstantsUtil;
import com.abdymalikmulky.bukapuasaapp.util.DateTimeUtil;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;
import timber.log.Timber;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity implements CountdownView.OnCountdownEndListener, MainContract.View{

    private Intent intent;
    private int cityId;

    private MainContract.Presenter mainPresenter;

    //TODO: MVP nih harusnya
    private CitySp citySp;

    private JadwalRemote jadwalRemote;
    private JadwalLocal jadwalLocal;
    private JadwalRepo jadwalRepo;


    private Toolbar toolbar;
    private TextView tvDateMasehi, tvDateHijr, tvLabelMaghrib, tvTimeMaghrib, tvCityName;

    private DateTimeUtil dateTimeUtil;

    private String dateMaghrib = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        intent = getIntent();
        cityId = intent.getIntExtra(ConstantsUtil.INTENT_EXTRA_CITY_ID, 0);

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
                .setShowSecond(true)
                .setShowMillisecond(false);

        mCvCountdownViewHour.dynamicShow(dynamicConfigBuilder.build());
    }
    private void setFindViewById(){

        toolbar = findViewById(R.id.toolbar);
        tvDateHijr = findViewById(R.id.tv_date_hijr);
        tvDateMasehi = findViewById(R.id.tv_date_masehi);
        tvLabelMaghrib = findViewById(R.id.tv_maghrib_label);
        tvTimeMaghrib = findViewById(R.id.tv_maghrib_time);
        tvCityName = findViewById(R.id.tv_city_name);


        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //SET CUSTOM ACTION BAR
        SpannableString s = new SpannableString(getString(R.string.app_title));
        s.setSpan(new TypefaceSpan("quicksand.xml"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView textview = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(layoutparams);
        textview.setText(s);
        textview.setTextColor(Color.WHITE);
        textview.setGravity(Gravity.CENTER);
        textview.setTextSize(20);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textview);


        //getSupportActionBar().setIcon(R.drawable.logo_icon);
        getSupportActionBar().hide();

    }
    private void initRepoPresenter(){
        citySp = new CitySp(getApplicationContext());

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
        mainPresenter.getNowJadwal(cityId);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mainPresenter = presenter;
    }

    @Override
    public void showJadwalToday(Jadwal jadwalToday) {
        String date = jadwalToday.getJadwalDate();
        String maghrib = jadwalToday.getMaghrib();
        dateMaghrib = date+" "+maghrib;

        long timeBukaPuasa = dateTimeUtil.getTimeDiff(dateMaghrib);
        setCountdown(timeBukaPuasa);

        tvTimeMaghrib.setText(DateTimeUtil.removeSecondInStringTime(maghrib));
        tvDateMasehi.setText(DateTimeUtil.getTodayIndonesia());
        tvDateHijr.setText(DateTimeUtil.getTodayHijr());

        String cityName = citySp.getCityName().substring(0, 1).toUpperCase() + citySp.getCityName().substring(1);
        tvCityName.setText(cityName);
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

