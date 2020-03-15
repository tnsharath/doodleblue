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

    private final Context context;

    private final AppDatabase db;



    public Repository(Context context, AppDatabase db) {
        this.context = context;
        this.db = db;
    }

    /**
     * Get all the menu.
     *
     * @return RestuarantMenu
     */
    public LiveData<List<RestuarantMenu>> getMenuList() {
        return db.menuDao().getAll();
    }

    /**
     * Schedule job to update menu daily
     */
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

    /**
     * getCart items
     *
     * @return cartItems
     */
    public LiveData<List<RestuarantMenu>> getCartList() {
        return db.menuDao().getCartMenu();
    }

    /**
     * Update table after choosing menu for order.
     *
     * @param restuarantMenu list of items
     */
    public void updateCart(RestuarantMenu restuarantMenu) {
        new ServerToRoomUpdate().updateTable(context, restuarantMenu);
    }
}
