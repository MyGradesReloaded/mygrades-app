package dh.mygrades.main.alarm;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;


import java.util.concurrent.TimeUnit;

import dh.mygrades.R;

/**
 * Manages all alarm operations to repeat scraping automatically.
 */
public class ScrapeWorkerManager {
    private static final String TAG = ScrapeWorkerManager.class.getSimpleName();
    private static final String WORKER_ID = "scrapeGrades";

    private static final int STANDARD_INTERVAL = 120;

    private Context context;

        // needed to enable / disable boot receiver
        private ComponentName bootReceiver;
        private PackageManager packageManager;

    public ScrapeWorkerManager(Context context) {
        this.context = context.getApplicationContext();
        
        this.bootReceiver = new ComponentName(this.context, BootReceiver.class);
        this.packageManager = this.context.getPackageManager();
    }

    public void setBackgroundScrapingFromPrefs(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean onlyWifi = prefs.getBoolean(context.getResources().getString(R.string.pref_key_only_wifi), true);
        int intervalMinutes = Integer.parseInt(prefs.getString(context.getResources().getString(R.string.pref_key_scrape_frequency), "" + STANDARD_INTERVAL));
        boolean isAutoScrapeEnabled = prefs.getBoolean(context.getResources().getString(R.string.pref_key_automatic_scraping), false);

        if(!isAutoScrapeEnabled){

            WorkManager.getInstance(context).cancelUniqueWork(WORKER_ID);
            Log.d(TAG, "Worker cancelled");
        }else {

            Constraints constraints = new Constraints.Builder().setTriggerContentMaxDelay(10, TimeUnit.MINUTES)
                    .setRequiredNetworkType(onlyWifi ? NetworkType.UNMETERED : NetworkType.CONNECTED)
                    .build();

            PeriodicWorkRequest scrapeWorker =
                    new PeriodicWorkRequest.Builder(ScrapeWorker.class, intervalMinutes, TimeUnit.MINUTES)
                            .setConstraints(constraints)
                            .setInitialDelay(1, TimeUnit.MINUTES)
                            .build();

            scrapeWorker.getId();
            Operation operation = WorkManager.getInstance(context).enqueueUniquePeriodicWork(WORKER_ID, ExistingPeriodicWorkPolicy.KEEP, scrapeWorker);

            Log.d(TAG, "Worker scheduled");
            // enable boot receiver to set alarm after reboot
            packageManager.setComponentEnabledSetting(bootReceiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            Log.d(TAG, "BootReceiver enabled!");
        }
    }
}
