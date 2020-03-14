package com.vintile.restaurantapp.di;

import com.vintile.restaurantapp.di.cart.CartModule;
import com.vintile.restaurantapp.di.cart.CartViewModelModule;
import com.vintile.restaurantapp.di.main.MainModule;
import com.vintile.restaurantapp.di.main.MainViewModelModule;
import com.vintile.restaurantapp.ui.cart.CartActivity;
import com.vintile.restaurantapp.ui.restuarantmenu.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Sharath on 2020/03/13
 **/

@Module
public abstract class ActivityBuildersModule {


    @ContributesAndroidInjector(
            modules = {
                    MainViewModelModule.class,
                    MainModule.class,
            }
    )
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(
            modules = {
                    CartViewModelModule.class,
                    CartModule.class
            }
    )
    abstract CartActivity contributeCartActivity();
}
