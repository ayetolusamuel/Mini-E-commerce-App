package com.codingwithset.productlist.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.codingwithset.productlist.model.Product;
import com.codingwithset.productlist.productInterface.ProductInterface;
import com.codingwithset.productlist.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository mRepository;

    public ProductViewModel(Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(application);
    }

    public LiveData<List<Product>> fetchProduct() {
        return mRepository.loadProductLocally();
    }

    public void insertProduct(List<Product> products) {
        mRepository.insertProduct(products);
    }

    public LiveData<List<Product>> getProductBaseCategory(String category) {
        return mRepository.getProductBaseCategory(category);
    }

    public LiveData<List<Product>> getProduct() {
        return mRepository.getProduct();
    }

    public void setListener(ProductInterface listener) {
        mRepository.setListener(listener);
    }


    public float getProductCount() {
        return mRepository.getProductCount();
    }



}
