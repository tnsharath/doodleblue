package com.vintile.restaurantapp.di.main;

import android.app.Application;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.vintile.restaurantapp.data.Repository;
import com.vintile.restaurantapp.network.MainApi;
import com.vintile.restaurantapp.ui.restuarantmenu.MainActivity;
import com.vintile.restaurantapp.ui.restuarantmenu.MenuAdapter;
import com.vintile.restaurantapp.util.MainAdapterInterface;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Sharath on 2020/02/14
 **/
@Module
public class MainModule {


    @Provides
    static MainAdapterInterface provideInterface(MainActivity mainActivity){
        return mainActivity;
    }

    @Provides
    static MenuAdapter provideAdapter(MainAdapterInterface mainAdapterInterface) {
        return new MenuAdapter(mainAdapterInterface);
    }

    @Provides
    static LinearLayoutManager provideLinearLayoutManager(MainActivity mainActivity) {
        return new LinearLayoutManager(mainActivity);
    }


    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @Provides
    static Repository providesRepository(Application application){
        return new Repository(application);
    }

}
