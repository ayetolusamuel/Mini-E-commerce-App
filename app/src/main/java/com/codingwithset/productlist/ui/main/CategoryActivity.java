package com.codingwithset.productlist.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codingwithset.productlist.R;
import com.codingwithset.productlist.adapter.ProductAdapter;
import com.codingwithset.productlist.executor.AppExecutors;
import com.codingwithset.productlist.model.Product;
import com.codingwithset.productlist.productInterface.ProductInterface;
import com.codingwithset.productlist.utils.NetworkStatus;
import com.codingwithset.productlist.utils.Utils;
import com.codingwithset.productlist.viewmodel.ProductViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProductViewModel mViewModel;
    private RelativeLayout mLayout;
    private ProductAdapter productAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private ImageButton mBackButton;
    private SearchView mSearchView;
    private RelativeLayout mRelativeLayout;
    private TextView mSearchProductErrorName, mNoConnectionError;
    private String productSearch;
    private static final String TAG = "CategoryActivity";
    private String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mRecyclerView = findViewById(R.id.recyclerview);
        mViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        mLayout = findViewById(R.id.rLayour);
        mRefreshLayout = findViewById(R.id.swipe);
        mSearchView = findViewById(R.id.search_product);
        mBackButton = findViewById(R.id.back_button);
        mRelativeLayout = findViewById(R.id.relLayout);
        mNoConnectionError = findViewById(R.id.no_connection_text_view);
        mSearchProductErrorName = findViewById(R.id.check_product_name_text_view);

        mBackButton.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        if (Utils.isFirstLaunch(this)) {
            if (!NetworkStatus.getInstance(this).isOnline()) {
                Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }

        }


        getIncomingIntent();
        getProduct();
        initSearchView();
        onSwipe();




    }

    private void mergeMeasuringAndIndustrial() {
        if (category.equals(getString(R.string.category_industrial_tools))){
            mRefreshLayout.setEnabled(false);
            List<Product> products = new ArrayList<>();
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String jsonMeasuring = sharedPrefs.getString(getString(R.string.measuring), "");
            String jsonIndustrial = sharedPrefs.getString(getString(R.string.industrial), "");

            Type type = new TypeToken<List<Product>>() {}.getType();
            List<Product> measuring = gson.fromJson(jsonMeasuring, type);
            List<Product> industrial = gson.fromJson(jsonIndustrial, type);

            try{
                if (measuring != null && industrial != null){
                    products.addAll(measuring);
                    products.addAll(industrial);
                    mLayout.setVisibility(View.GONE);
                    if (products != null){
                        productAdapter = new ProductAdapter(getApplicationContext(), products);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        mRecyclerView.setAdapter(productAdapter);
                        productAdapter.notifyDataSetChanged();
                        intentAction();
                    }

                }else{
                    Toast.makeText(this, "error if fetching data.....", Toast.LENGTH_SHORT).show();
                }

            }catch (NullPointerException ex){
                ex.printStackTrace();
                Log.d(TAG, "mergeMeasuringAndIndustrial: ");
            }
            }

    }

    private void getIncomingIntent() {
        try {
            Intent intent = getIntent();
            category = intent.getStringExtra(getString(R.string.intent_product));

        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void initSearchView() {
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager
                .getSearchableInfo(this.getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productAdapter.getFilter().filter(query);
                searchItemisEmptyView();
                if (productAdapter.getItemCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No result for search product!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                productSearch = query;
                productAdapter.getFilter().filter(query);
                searchItemisEmptyView();
                return false;
            }
        });


    }

    private void searchItemisEmptyView() {
        productAdapter.setOnFilterProductListener(count -> {
            if (count < 1) {
                runOnUiThread(() -> {
                    mSearchProductErrorName.setText(getString(R.string.error_message, productSearch));
                    mRelativeLayout.setVisibility(View.VISIBLE);
                });

            } else {
                runOnUiThread(() -> mRelativeLayout.setVisibility(View.GONE));
            }
        });

    }

    private void getProduct() {
        mLayout.setVisibility(View.VISIBLE);

        if (category != null) {
            Log.d(TAG, "getProduct: " + category);
            if (!category.equals(getString(R.string.category_industrial_tools))) {
                mViewModel.getProductBaseCategory(category).observe(this, products -> {
                    mLayout.setVisibility(View.GONE);
                    productAdapter = new ProductAdapter(getApplicationContext(), products);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mRecyclerView.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();
                    intentAction();
                });
            }else{
                mergeMeasuringAndIndustrial();
            }

        }

    }

    private void intentAction() {
        productAdapter.setOnclickListener((view, product) -> {
            Intent intent1 = new Intent(view.getContext(), DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(view.getContext().getResources().getString(R.string.details_intent_product), product);
            intent1.putExtras(bundle);
            startActivity(intent1);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    private void onSwipe() {
        mRefreshLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "onRefresh: ");
            mViewModel.fetchProduct();
            mViewModel.setListener(new ProductInterface() {
                @Override
                public void onStarted() {
                    Log.d(TAG, "onStarted: ");
                }

                @Override
                public void onSuccess(List<Product> productList) {
                    Log.d(TAG, "onSuccess: " + productList.size());
                    productAdapter.setFilteredProduct(productList);
                    mViewModel.insertProduct(productList);
                    Toast.makeText(CategoryActivity.this, "Record updated successfully", Toast.LENGTH_SHORT).show();
                    mRefreshLayout.setEnabled(false);

                }

                @Override
                public void onFailed(String message) {
                    Log.d(TAG, "onFailed: ");
                    mRefreshLayout.setEnabled(false);
                }

            });
        });

    }


}
