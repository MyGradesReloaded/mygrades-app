package dh.mygrades;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import androidx.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

import dh.mygrades.database.DatabaseHelper;
import dh.mygrades.database.dao.DaoMaster;
import dh.mygrades.database.dao.DaoSession;
import dh.mygrades.main.alarm.ScrapeAlarmManager;

/**
 * MyGradesApplication to hold the DaoSession in application scope.
 */
public class MyGradesApplication extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // set preferences default values
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        SQLiteOpenHelper helper = new DatabaseHelper(this, "mygrades.db", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();

        // check if alarm for automatic scraping is set
        ScrapeAlarmManager scrapeAlarmManager = new ScrapeAlarmManager(this);
        scrapeAlarmManager.setAlarmFromPrefs(false, false);

        // set dark mode on supported devices
        if(Build.VERSION.SDK_INT >= 29) AppCompatDelegate.setDefaultNightMode(Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(getString(R.string.pref_key_dark_theme), "-1")));
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
