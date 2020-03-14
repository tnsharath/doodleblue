package com.vintile.restaurantapp.data;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.vintile.restaurantapp.db.AppDatabase;
import com.vintile.restaurantapp.models.RestuarantMenu;
import com.vintile.restaurantapp.network.ServerToRoomUpdate;
import com.vintile.restaurantapp.services.DbUpdateService;
import com.vintile.restaurantapp.util.AppConstants;

import java.util.List;
import java.util.Objects;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

/**
 * Created by Sharath on 2020/03/13
 **/
public class Repository {

    private LiveData<List<RestuarantMenu>> menuList;

    private Context context;
    AppDatabase db;
    public Repository(Context context){
        db = AppDatabase.getInstance(context);
        //TODO inject my application
        this.context = context;

    }

    public LiveData<List<RestuarantMenu>> getMenuList() {
        return db.menuDao().getAll();
    }

    public void scheduleJobGetBonusProducts() {
        JobScheduler jobScheduler = (JobScheduler) context
                .getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(context,
                DbUpdateService.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setPeriodic(AppConstants.schedulerTime)
                .setPersisted(true).build();
        Objects.requireNonNull(jobScheduler).schedule(jobInfo);
    }

    public LiveData<List<RestuarantMenu>> getCartList() {
        return  db.menuDao().getCartMenu();
    }

    public void updateCart(RestuarantMenu restuarantMenu) {
        new ServerToRoomUpdate().updateTable(context, restuarantMenu);
    }


}
