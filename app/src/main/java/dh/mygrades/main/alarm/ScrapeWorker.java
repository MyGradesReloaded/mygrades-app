package dh.mygrades.main.alarm;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import dh.mygrades.main.MainServiceHelper;
import dh.mygrades.main.processor.GradesProcessor;

public class ScrapeWorker extends Worker {

    public ScrapeWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        GradesProcessor gradesProcessor = new GradesProcessor(getApplicationContext());
        gradesProcessor.scrapeForGrades(false, true);
        return Result.success();
    }
}