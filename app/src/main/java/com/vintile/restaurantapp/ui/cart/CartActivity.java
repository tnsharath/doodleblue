package com.vintile.restaurantapp.ui.cart;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vintile.restaurantapp.R;
import com.vintile.restaurantapp.models.RestuarantMenu;
import com.vintile.restaurantapp.ui.restuarantmenu.MenuAdapter;
import com.vintile.restaurantapp.util.MainAdapterInterface;
import com.vintile.restaurantapp.util.VerticalSpacingItemDecoration;
import com.vintile.restaurantapp.viewmodels.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

//TODO total count;
public class CartActivity extends DaggerAppCompatActivity implements MainAdapterInterface {

    private static final String TAG = "MainActivity";

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

        tvShowMore.setOnClickListener(v -> {
            showMore();
        });
    }

    private void showMore() {
        adapter.refreshList(cartMenu);
        tvShowMore.setVisibility(View.INVISIBLE);
    }
    private List<RestuarantMenu> cartMenu = new ArrayList<>();

    double price = 0.0;
    //TODO show more button
    private void subscribeObserver() {
        viewModel.getMenu().observe(this, menus -> {
            Log.d(TAG, "onChanged: Success " + menus.size());
            cartMenu = menus;


            for (RestuarantMenu menu: cartMenu){
                price += Double.parseDouble(menu.getPrice()) * Double.parseDouble(menu.getItemCount());
            }

            tvTotalCost.setText(String.valueOf(price));
            if (menus.size() > 2){
                List<RestuarantMenu> sortedMenu = new ArrayList<>();
                sortedMenu.add(menus.get(0));
                sortedMenu.add(menus.get(1));
                tvShowMore.setVisibility(View.VISIBLE);
                adapter.setMenus(sortedMenu);
            }else{
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
    public void updateCount(int count) {

    }


    @Override
     public void updatePrice(Map<String, RestuarantMenu> cartChoice) {

        Iterator it = cartChoice.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            RestuarantMenu menu = (RestuarantMenu) pair.getValue();
            price += Integer.parseInt(menu.getPrice()) * Integer.parseInt(menu.getItemCount());

            it.remove(); // avoids a ConcurrentModificationException
        }


        tvTotalCost.setText(String.valueOf(price));

    }
    public void pay(View view) {
    }
}
