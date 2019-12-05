package com.codingwithset.productlist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.codingwithset.productlist.model.Product;

@Database(entities = Product.class,
        version = 1,
        exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {


    private static ProductDatabase INSTANCE;
    private static final String DATABASE_NAME = "product";
    private static Object lock = new Object();

    public abstract ProductDao productDao();

    public static ProductDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (lock) {
                if (INSTANCE == null) {
                    //create database Here......
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProductDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}
