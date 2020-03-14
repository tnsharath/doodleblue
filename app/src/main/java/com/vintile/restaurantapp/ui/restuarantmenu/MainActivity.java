package com.vintile.restaurantapp.ui.restuarantmenu;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import com.vintile.restaurantapp.R;
import com.vintile.restaurantapp.models.RestuarantMenu;
import com.vintile.restaurantapp.ui.cart.CartActivity;
import com.vintile.restaurantapp.util.MainAdapterInterface;
import com.vintile.restaurantapp.util.VerticalSpacingItemDecoration;
import com.vintile.restaurantapp.viewmodels.ViewModelProviderFactory;


import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements MainAdapterInterface {

    private static final String TAG = "MainActivity";

    private MainViewModel viewModel;
    Button btnCheckout;

    @Inject
    MenuAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    LinearLayoutManager linearLayoutManager;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work);

        recyclerView = findViewById(R.id.rv_menu);
        btnCheckout = findViewById(R.id.btnCheckout);
        viewModel = new ViewModelProvider(this, providerFactory).get(MainViewModel.class);
        initRecyclerView();
        subscribeObserver();


        btnCheckout.setOnClickListener(v -> {
            updateCartTable(adapter.getCartItem());
            startActivity(new Intent(this, CartActivity.class));
        });
    }

    private void subscribeObserver() {
        viewModel.getDataFromAPI();
        viewModel.getMenu().observe(this, menus -> {
            Log.d(TAG, "onChanged: Success");
            adapter.setMenus(menus);
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void updateCount(int count) {
        if (count > 0) {
            btnCheckout.setEnabled(true);
            btnCheckout.setText(" VIEW CART (" + count + " ITEMS)");
        } else {
            btnCheckout.setEnabled(false);
            btnCheckout.setText(" VIEW CART (Empty)");
        }

    }

    private void updateCartTable(Map<String, RestuarantMenu> cartChoice) {
        viewModel.updateCartTable(cartChoice);
    }

    @Override
    public void updatePrice(Map<String, RestuarantMenu> cartChoice) {

    }
}
