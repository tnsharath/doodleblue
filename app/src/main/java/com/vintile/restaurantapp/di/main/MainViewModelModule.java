package com.vintile.restaurantapp.di.main;

import androidx.lifecycle.ViewModel;

import com.vintile.restaurantapp.di.ViewModelKey;
import com.vintile.restaurantapp.ui.restuarantmenu.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Sharath on 2020/02/12
 **/
@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel viewModel);
}
