package com.vintile.restaurantapp.ui.cart;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vintile.restaurantapp.R;
import com.vintile.restaurantapp.models.RestuarantMenu;
import com.vintile.restaurantapp.ui.restuarantmenu.MenuAdapter;
import com.vintile.restaurantapp.util.MainAdapterInterface;
import com.vintile.restaurantapp.util.VerticalSpacingItemDecoration;
import com.vintile.restaurantapp.viewmodels.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class CartActivity extends DaggerAppCompatActivity implements MainAdapterInterface {

    private CartViewModel viewModel;

    @Inject
    MenuAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    LinearLayoutManager linearLayoutManager;

    private RecyclerView recyclerView;
    private TextView tvTotalCost;

    private TextView tvShowMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.rv_cart);
        tvTotalCost = findViewById(R.id.tvTotalCost);
        tvShowMore = findViewById(R.id.tvShowMore);
        viewModel = new ViewModelProvider(this, providerFactory).get(CartViewModel.class);
        initRecyclerView();
        subscribeObserver();
        updateTotalCost();
        tvShowMore.setOnClickListener(v -> showMore());
    }

    private void updateTotalCost() {
        viewModel.getTotalPrice().observe(this, cost -> tvTotalCost.setText(String.valueOf(cost)));
    }

    private void showMore() {
        adapter.refreshList(cartMenu);
        tvShowMore.setVisibility(View.INVISIBLE);
    }

    private List<RestuarantMenu> cartMenu = new ArrayList<>();

    private void subscribeObserver() {
        viewModel.getMenu().observe(this, menus -> {
            cartMenu = menus;

            for (RestuarantMenu menu : cartMenu) {
                viewModel.setCartList(menu);
            }
            if (menus.size() > 2) {
                List<RestuarantMenu> sortedMenu = new ArrayList<>();
                sortedMenu.add(menus.get(0));
                sortedMenu.add(menus.get(1));
                tvShowMore.setVisibility(View.VISIBLE);
                adapter.setMenus(sortedMenu);
            } else {
                adapter.setMenus(cartMenu);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void updatePrice(RestuarantMenu cartChoice) {
        viewModel.setCartList(cartChoice);
    }
}
