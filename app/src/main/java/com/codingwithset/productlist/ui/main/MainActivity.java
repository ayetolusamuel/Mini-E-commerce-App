package com.codingwithset.productlist.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.codingwithset.productlist.R;
import com.codingwithset.productlist.databinding.ActivityMainBinding;
import com.codingwithset.productlist.viewmodel.ProductViewModel;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mBinding;
    ProductViewModel mViewModel;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        mBinding.setViewmodel(this);


    }

    public void categoryClick(View view) {

        int categoryId = view.getId();
        Log.d(TAG, "categoryClick: " + categoryId);
        if (categoryId == R.id.img_smart_home || categoryId == R.id.txt_smart_home) {
            getData(getString(R.string.category_smart_home));
            return;

        }
        if (categoryId == R.id.img_electronic || categoryId == R.id.txt_electronic) {
            getData(getString(R.string.category_electronics_gadgets));
            return;
        }
        if (categoryId == R.id.img_agriculture || categoryId == R.id.txt_agriculture) {
            getData(getString(R.string.category_agricultural_products));
            return;
        }
        if (categoryId == R.id.img_industrial || categoryId == R.id.txt_industrial_tools) {
            getData(getString(R.string.category_industrial_tools));
            return;
        }
        if (categoryId == R.id.img_auto_care || categoryId == R.id.txt_auto_care) {
            getData(getString(R.string.category_automotive));
            return;
        }
        if (categoryId == R.id.img_safety || categoryId == R.id.txt_safety) {
            getData(getString(R.string.category_safety));
            return;
        }
        if (categoryId == R.id.img_home || categoryId == R.id.txt_home) {
            getData(getString(R.string.category_household));
            return;
        }
        if (categoryId == R.id.img_kids || categoryId == R.id.txt_kids) {
            getData(getString(R.string.category_kids));
            return;

        }

    }

    private void getData(String category) {
        Log.d(TAG, "getData: " + category);
        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
        intent = intent.putExtra(getString(R.string.intent_product), category);
        startActivity(intent);


    }

}
