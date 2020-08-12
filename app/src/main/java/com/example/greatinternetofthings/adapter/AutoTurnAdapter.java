package com.example.greatinternetofthings.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.greatinternetofthings.R;
import com.example.greatinternetofthings.constant.ConstantAssemble;

import java.util.List;
import java.util.zip.Inflater;

public class AutoTurnAdapter extends PagerAdapter {

    Context mContext;
    List<String> imagePath;
    int type;

    public AutoTurnAdapter(Context context) {
        mContext = context;
        type = ConstantAssemble.AUTO_TRUN_NATIVE;
    }

    public AutoTurnAdapter(Context context, List<String> imagePath) {
        mContext = context;
        this.imagePath = imagePath;
        type = ConstantAssemble.AUTO_TRUN_NET;
    }

    @Override
    public int getCount() {
        return ConstantAssemble.nativeImagePath.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        ViewPager vp = (ViewPager) container;
        int position = vp.getCurrentItem();
        Log.d("TAG", "update position ---  "+position);
        int length = ConstantAssemble.nativeImagePath.length;
        if (position == 0) {
            position = length - 2;
            vp.setCurrentItem(position, false);
        } else if (position == length - 1) {
            position = 1;
            vp.setCurrentItem(position, false);
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_auto_turn, null);
        ImageView ivContent = view.findViewById(R.id.iv_content);
        if (type == ConstantAssemble.AUTO_TRUN_NATIVE) {
            ivContent.setImageResource(ConstantAssemble.nativeImagePath[position]);
        } else {
            ivContent.setImageURI(Uri.parse(imagePath.get(position)));
        }
        container.addView(view);
        return view;
    }
}
