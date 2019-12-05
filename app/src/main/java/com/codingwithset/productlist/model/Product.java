package com.codingwithset.productlist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import android.util.Log;

import org.jetbrains.annotations.NotNull;


@Entity(tableName = "products",indices =  @Index(value = {"id"},unique = true))
public class Product implements Parcelable {



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@org.jetbrains.annotations.NotNull String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInstock() {
        return instock;
    }

    public void setInstock(String instock) {
        this.instock = instock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    @NotNull
    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public static Creator<Product> getCREATOR() {
        return CREATOR;
    }

    public Product() {

    } public Product(String productString) {


        Log.d("", "Product##: "+productString);

    }

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @NonNull

    private String id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @NonNull
    @ColumnInfo(name = "price")
    private String price;
    @NonNull
    @ColumnInfo(name = "instock")
    private String instock;
    @NonNull
    @ColumnInfo(name = "category")
    private String category;
    @NonNull
    @ColumnInfo(name = "sub_category")
    private String subCategory;
    @NonNull
    @ColumnInfo(name = "img1")
    private String img1;
    @NonNull
    @ColumnInfo(name = "img2")
    private String img2;
    @NonNull
    @ColumnInfo(name = "img3")
    private String img3;
    @ColumnInfo(name = "sku_code")
    private String skuCode;


    protected Product(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        price = in.readString();
        instock = in.readString();
        category = in.readString();
        subCategory = in.readString();
        img1 = in.readString();
        img2 = in.readString();
        img3 = in.readString();
        skuCode = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(instock);
        dest.writeString(category);
        dest.writeString(subCategory);
        dest.writeString(img1);
        dest.writeString(img2);
        dest.writeString(img3);
        dest.writeString(skuCode);
    }


    @Ignore public Product(@NonNull String id, @NonNull String name, @NonNull String description, @NonNull String price, @NonNull String instock, @NonNull String category, @NonNull String subCategory, @NonNull String img1, @NonNull String img2, @NonNull String img3, @NonNull String skuCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.instock = instock;
        this.category = category;
        this.subCategory = subCategory;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.skuCode = skuCode;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", instock='" + instock + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", img1='" + img1 + '\'' +
                ", img2='" + img2 + '\'' +
                ", img3='" + img3 + '\'' +
                ", skuCode='" + skuCode + '\'' +
                '}';
    }
}
