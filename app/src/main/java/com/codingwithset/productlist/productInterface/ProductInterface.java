package com.codingwithset.productlist.productInterface;

import com.codingwithset.productlist.model.Product;

import java.util.List;

public interface ProductInterface {
    void onStarted();
    void onSuccess(List<Product> productList);
    void onFailed(String message);
}
