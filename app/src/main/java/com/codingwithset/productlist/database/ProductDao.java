package com.codingwithset.productlist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.codingwithset.productlist.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAllProduct(List<Product> products);

    @Query("SELECT * FROM products ORDER BY name")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM products WHERE category =:category ORDER BY name")
    LiveData<List<Product>> getProductBaseCategory(String category);

    @Query("SELECT count(*) FROM products")
    Long getDatabaseCount();


}
