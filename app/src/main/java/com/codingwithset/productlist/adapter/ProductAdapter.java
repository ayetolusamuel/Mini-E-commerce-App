package com.codingwithset.productlist.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.codingwithset.productlist.R;
import com.codingwithset.productlist.model.Product;
import com.codingwithset.productlist.productInterface.OnFilterProductListener;
import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewModel> implements Filterable {
    private List<Product> mProducts;
    private List<Product> mFilteredProduct;
    private Context mContext;
    private OnclickListener mOnclickListener;
    private OnFilterProductListener mOnFilterProduct;



    public ProductAdapter(Context context, List<Product> products) {
        mContext = context;
        mFilteredProduct = products;
        mProducts = products;
    }


    public void setOnFilterProductListener(OnFilterProductListener onFilterProduct) {
        mOnFilterProduct = onFilterProduct;
    }

    @NonNull
    @Override
    public ProductViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_list_item, parent, false);

        return new ProductViewModel(view);


    }


    public void setFilteredProduct(List<Product> filteredProduct) {
        mFilteredProduct = filteredProduct;
        notifyDataSetChanged();
    }

    public void setOnclickListener(OnclickListener onclickListener) {
        mOnclickListener = onclickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewModel holder, int position) {

        Product product = mFilteredProduct.get(position);
        holder.mName.setText(product.getName());
        holder.mPrice.setText(mContext.getString(R.string.product_price, product.getPrice()));
        Glide.with(mContext)
                .load(product.getImg1())
                .placeholder(R.drawable.image_loading)
                .into(holder.mImage);


    }

    @Override
    public int getItemCount() {
        return mFilteredProduct.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
               if (charString.isEmpty()) {
                    mFilteredProduct.clear();
                    mFilteredProduct = mProducts;
                } else {
                    ArrayList<Product> filteredList = new ArrayList<>();
                    for (Product row : mProducts) {

                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                  mOnFilterProduct.onProductFilterCount(filteredList.size());

                    mFilteredProduct = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredProduct;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mFilteredProduct = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ProductViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mName, mPrice;
        ImageView mImage;


        ProductViewModel(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mPrice = itemView.findViewById(R.id.price);
            mImage = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnclickListener.onProductClick(view,mFilteredProduct.get(getAdapterPosition()));

        }
    }
   public interface OnclickListener{
        void onProductClick(View view,Product product);
    }
}
