package com.vintile.restaurantapp.di;
import android.app.Application;

import com.vintile.restaurantapp.data.Repository;
import com.vintile.restaurantapp.db.AppDatabase;
import com.vintile.restaurantapp.network.MainApi;
import com.vintile.restaurantapp.network.ServerToRoomUpdate;
import com.vintile.restaurantapp.util.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sharath on 2020/03/13
 **/
@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static Retrofit providesRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    static Repository providesRepository(Application application, AppDatabase appDatabase){
        return new Repository(application, appDatabase);
    }

    @Provides
    static AppDatabase providesDatabaseInstance(Application application){
        return AppDatabase.getInstance(application);
    }


    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @Provides
    static ServerToRoomUpdate providesServerToRoom(){
        return new ServerToRoomUpdate();
    }
}
