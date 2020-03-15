package com.vintile.restaurantapp.di.main;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.vintile.restaurantapp.ui.restuarantmenu.MainActivity;
import com.vintile.restaurantapp.ui.restuarantmenu.MenuAdapter;
import com.vintile.restaurantapp.util.MainAdapterInterface;
import com.vintile.restaurantapp.util.VerticalSpacingItemDecoration;

import dagger.Module;
import dagger.Provides;

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
    static VerticalSpacingItemDecoration providesVerticalSpace(){
        return new VerticalSpacingItemDecoration(15);
    }


}
