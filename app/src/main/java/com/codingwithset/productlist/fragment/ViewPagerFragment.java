package com.codingwithset.productlist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codingwithset.productlist.R;

public class ViewPagerFragment extends Fragment {

    private ImageView mImageView;
    private String mImageUrl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            mImageUrl = bundle.getString(getString(R.string.image_intent_product));
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_product_fragment_viewpager, container, false);
        mImageView = view.findViewById(R.id.image);
        setProduct();
        return view;
    }

    private void setProduct() {
       RequestOptions requestOptions = RequestOptions.fitCenterTransform()
                .transform(new RoundedCorners(20))
                .placeholder(R.drawable.image_loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .dontTransform()
                .dontAnimate()
               .override(mImageView.getWidth(),mImageView.getHeight());


        Glide.with(getActivity())
                .setDefaultRequestOptions(requestOptions)
                .load(mImageUrl)
                .into(mImageView);


    }

}