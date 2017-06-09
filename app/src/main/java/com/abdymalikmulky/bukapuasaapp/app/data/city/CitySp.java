package com.abdymalikmulky.bukapuasaapp.app.data.city;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 6/9/17.
 */

public class CitySp {

    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private static final String prefName = "pref_city";
    private static final String KEY_CITY_NAME = "city_name";
    private static final String KEY_CITY_ID = "city_id";


    public CitySp(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(prefName, MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void setCityName(String cityName){
        cityName = cityName.toLowerCase();
        editor.putString(KEY_CITY_NAME, cityName);
        editor.commit();
    }

    public String getCityName(){
        String cityName = sharedPref.getString(KEY_CITY_NAME, "jakarta");
        return cityName;
    }

    public void setCityId(int cityId){
        editor.putInt(KEY_CITY_NAME, cityId);
        editor.commit();
    }

    public int getCityId(){
        int cityId = sharedPref.getInt(KEY_CITY_NAME, 14);
        return cityId;
    }

}
