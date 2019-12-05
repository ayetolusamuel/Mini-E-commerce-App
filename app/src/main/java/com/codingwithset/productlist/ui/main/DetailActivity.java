package com.codingwithset.productlist.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codingwithset.productlist.R;
import com.codingwithset.productlist.utils.ZoomOutPageTransformer;
import com.codingwithset.productlist.adapter.ProductPagerAdapter;
import com.codingwithset.productlist.fragment.ViewPagerFragment;
import com.codingwithset.productlist.model.Product;
import com.codingwithset.productlist.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DetailActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private TextView mPrice, mName, mDescription;
    private ImageButton mExpandDescription;
    private ViewPager mViewPager;
    private int mPage;
    private Timer mTimer;
    private int descriptionCount = 0;
    private Product mProduct;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        mPrice = findViewById(R.id.price);
        mName = findViewById(R.id.name);
        mDescription = findViewById(R.id.product_description);
        mExpandDescription = findViewById(R.id.expand_desc);
        mViewPager = findViewById(R.id.product_container);
        mTabLayout = findViewById(R.id.tab_layout);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_view);
        navigationView.setOnNavigationItemSelectedListener(this);

        getProductIntent();

    }

    private void initPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        if (mProduct != null) {
            String img1, img2, img3;
            img1 = mProduct.getImg1();
            img2 = mProduct.getImg2();
            img3 = mProduct.getImg3();
            String[] imageUrlPath = {img1, img2, img3};

            for (String image : imageUrlPath) {
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.image_intent_product), image);
                ViewPagerFragment viewProductFragment = new ViewPagerFragment();
                viewProductFragment.setArguments(bundle);
                fragments.add(viewProductFragment);

            }
        }

        ProductPagerAdapter pagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mTabLayout.setupWithViewPager(mViewPager);


    }


    private void getProductIntent() {

        try {
            Intent intent = getIntent();
            if (intent != null) {
                mProduct = intent.getParcelableExtra(getString(R.string.details_intent_product));
                if (mProduct != null) {
                    mName.setText(mProduct.getName());
                    mPrice.setText(getString(R.string.product_price, mProduct.getPrice()));
                    String description = mProduct.getDescription();
                    description = description.replace("description", "Features");
                    description = description.replace("Description", "Features");
                    mDescription.setText(description.trim());
                }

                visibleDescrption();
                initPager();
                pageSwitcher(1);

            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    private void visibleDescrption() {

        mExpandDescription.setOnClickListener(view -> {
            descriptionCount++;
            if (descriptionCount == 1) {
                mExpandDescription.setBackground(getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                mDescription.setVisibility(View.VISIBLE);
            } else {
                descriptionCount = 0;
                mExpandDescription.setBackground(getResources().getDrawable(R.drawable.ic_chevron_right_black_24dp));
                mDescription.setVisibility(View.GONE);
            }
        });
    }


    public void pageSwitcher(int seconds) {
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 3000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_call: {
                Utils.callSeller(this);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            }
            case R.id.navigation_sms: {
                Utils.smsSeller(this, mProduct);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            }
            case R.id.navigation_chat: {
                Utils.openWhatsapp(this);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            }
        }

        return true;
    }

    private class RemindTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(() -> {
                if (mPage > 4) {
                    mTimer.cancel();
                } else {
                    if (mPage == 3)
                        mPage = 0;
                    mViewPager.setCurrentItem(mPage++);


                }
            });

        }

    }


}
