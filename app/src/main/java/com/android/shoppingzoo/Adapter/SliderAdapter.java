package com.android.shoppingzoo.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.shoppingzoo.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {
     private List<String> mSliderItems = new ArrayList<>();



        public void renewItems(List<String> sliderItems) {
            this.mSliderItems = sliderItems;
            notifyDataSetChanged();
        }



        @Override
        public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_holder, null);
            return new SliderAdapterVH(inflate);
        }

        @Override
        public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
            Log.d("iamgedebu",mSliderItems.get(position));

            viewHolder.spinKitView.setVisibility(View.VISIBLE);
            Picasso.get().load( mSliderItems.get(position)).fit().into(viewHolder.imageview, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("iamgedebu","sucess");
                    viewHolder.spinKitView.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("iamgedebu",e.getMessage());
                }
            });


        }

        @Override
        public int getCount() {
            //slider view count could be dynamic size
            return mSliderItems.size();
        }

        class SliderAdapterVH extends ViewHolder {
            View itemView;
            ImageView imageview;
            ProgressBar spinKitView;
            public SliderAdapterVH(View itemView) {
                super(itemView);
                 imageview = itemView.findViewById(R.id.imageSlider);
                this.itemView = itemView;
                spinKitView = itemView.findViewById(R.id.progress_bar);
            }
        }

    }

