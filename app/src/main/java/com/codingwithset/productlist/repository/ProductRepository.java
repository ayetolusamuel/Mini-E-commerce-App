package com.codingwithset.productlist.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.codingwithset.productlist.R;
import com.codingwithset.productlist.database.ProductDao;
import com.codingwithset.productlist.database.ProductDatabase;
import com.codingwithset.productlist.executor.AppExecutors;
import com.codingwithset.productlist.model.Product;
import com.codingwithset.productlist.productInterface.ProductInterface;
import com.codingwithset.productlist.utils.NetworkStatus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProductRepository {

    private static final String TAG = "ProductRepository";
    private ProductDao productDao;
    private static ProductRepository instance;
    private ProductInterface mListener;
    private Context mContext;
    float Count;
    private MutableLiveData<List<Product>> mProducts = new MediatorLiveData<>();

    public void setListener(ProductInterface listener) {
        mListener = listener;
    }


    public static ProductRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ProductRepository(context);
        }
        return instance;
    }

    private ProductRepository(Context context) {
        this.mContext = context.getApplicationContext();
        productDao = ProductDatabase.getInstance(context).productDao();
        init();


    }


    public float getCount() {
        return Count;
    }

    public void setCount(float count) {
        Count = count;
    }

    private void init() {
        mProducts.observeForever(products -> {
            AppExecutors.getInstance().diskIO().execute(() ->

                    productDao.insertAllProduct(products)


            );

        });
    }


    public void fetchDataFromServer() {
        List<Product> productList = new ArrayList<>();
        if (mListener != null) {
            mListener.onStarted();
        }
        if (NetworkStatus.getInstance(mContext).isOnline()) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(mContext.getString(R.string.product_list));
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        productList.add(snapshot.getValue(Product.class));
                    }


                    fetchProduct(productList);
                    mListener.onSuccess(productList);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    if (mListener != null)
                        mListener.onFailed(databaseError.getMessage());


                }
            });

        }

    }

    private void fetchProduct(List<Product> productList) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            float id = productDao.getDatabaseCount();
            try {
                if (id != 0) {
                    Log.d(TAG, "fetchProduct: " + id);
                    AppExecutors.getInstance().mainThread().execute(() -> mProducts.setValue(productList));

                } else {
                    Log.d(TAG, "run: database is empty...");
                    productDao.insertAllProduct(productList);
                }
            } catch (Exception ex) {
                Log.d(TAG, "fetchProduct: " + ex.getMessage());
            }


        });


    }


    public LiveData<List<Product>> loadProductLocally() {
        fetchDataFromServer();
        return productDao.getAllProducts();
    }


    public LiveData<List<Product>> getProductBaseCategory(String category) {
        return productDao.getProductBaseCategory(category);

    }

    public void insertProduct(List<Product> products) {
        AppExecutors.getInstance().diskIO().execute(() -> productDao.insertAllProduct(products));
    }


    public LiveData<List<Product>> getProduct() {
        return productDao.getAllProducts();
    }


    public float getProductCount() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            float count = productDao.getDatabaseCount();
            AppExecutors.getInstance().mainThread().execute(() -> setCount(count));
        });

        return getCount();
    }
}
