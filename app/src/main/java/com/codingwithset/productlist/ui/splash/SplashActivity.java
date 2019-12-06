package com.codingwithset.productlist.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.codingwithset.productlist.R;
import com.codingwithset.productlist.model.Product;
import com.codingwithset.productlist.productInterface.ProductInterface;
import com.codingwithset.productlist.ui.main.MainActivity;
import com.codingwithset.productlist.viewmodel.ProductViewModel;

import java.util.List;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
  @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ProductViewModel  mViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        mViewModel.fetchProduct();
        mViewModel.setListener(new ProductInterface() {
            @Override
            public void onStarted() {
                Log.d(TAG, "onStarted: ");
            }

            @Override
            public void onSuccess(List<Product> productList) {
                Log.d(TAG, "onSuccess: "+productList.size());
            }

            @Override
            public void onFailed(String message) {
                Log.d(TAG, "onFailed: "+message);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            finish();
        }, 3000);

    }

}