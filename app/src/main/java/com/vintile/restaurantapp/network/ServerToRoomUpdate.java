package com.vintile.restaurantapp.network;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.vintile.restaurantapp.db.AppDatabase;
import com.vintile.restaurantapp.models.RestuarantMenu;
import com.vintile.restaurantapp.util.AppConstants;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sharath TN on 2019-07-12.
 */
public class ServerToRoomUpdate {

    private AppDatabase appDatabase;
    private final TaskExecutor taskExecutor;


    public ServerToRoomUpdate() {
        taskExecutor = new TaskExecutor();
    }

    private static final String TAG = "ServerToRoomUpdate";
    MainApi mainApi;
    public void loadBonusList(final Context context) {

        appDatabase = AppDatabase.getInstance(context);


   //     Call<List<RestuarantMenu>> call =  mainApi.getRestuarantMenu();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
           //     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MainApi api = retrofit.create(MainApi.class);
        Call<List<RestuarantMenu>> call = api.getRestuarantMenu();

        call.enqueue(new Callback<List<RestuarantMenu>>() {
            @Override
            public void onResponse(Call<List<RestuarantMenu>> call, Response<List<RestuarantMenu>> response) {
                taskExecutor.execute(new RoomUpdateTask(response.body()));
            }

            @Override
            public void onFailure(Call<List<RestuarantMenu>> call, Throwable t) {
            }
        });
    }

    public void updateTable(final Context context, RestuarantMenu menu){
        appDatabase = AppDatabase.getInstance(context);
        taskExecutor.execute(new UpdateDB(menu));
    }

    private class TaskExecutor implements Executor {
        @Override
        public void execute(@NonNull Runnable runnable) {
            Thread t = new Thread(runnable);
            t.start();
        }
    }

    private class RoomUpdateTask implements Runnable {
        private final List<RestuarantMenu> restuarantMenus;

        RoomUpdateTask(List<RestuarantMenu> restuarantMenus) {
            this.restuarantMenus = restuarantMenus;
        }

        @Override
        public void run() {
            insertLatestCouponsIntoLocalDb(restuarantMenus);
        }

    }

    private void insertLatestCouponsIntoLocalDb(List<RestuarantMenu> bonusProductsModels) {
        try {
            appDatabase.menuDao().insertAll(bonusProductsModels);
        }catch (Exception e){

        }
    }

    private class UpdateDB implements Runnable {
        private final RestuarantMenu restuarantMenus;

        UpdateDB(RestuarantMenu restuarantMenus) {
            this.restuarantMenus = restuarantMenus;
        }

        @Override
        public void run() {
            try {
                appDatabase.menuDao().updateCart(restuarantMenus);
            }catch (Exception e){
                Log.d(TAG, "run: unsucess" + e);
            }
        }

    }
}
