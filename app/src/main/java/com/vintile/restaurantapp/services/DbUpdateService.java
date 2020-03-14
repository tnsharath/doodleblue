package com.vintile.restaurantapp.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.vintile.restaurantapp.network.ServerToRoomUpdate;

/**
 * Created by Sharath TN on 2019-07-12.
 */
public class DbUpdateService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        ServerToRoomUpdate serverToRoomUpdate = new ServerToRoomUpdate();
        serverToRoomUpdate.loadBonusList(this);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
