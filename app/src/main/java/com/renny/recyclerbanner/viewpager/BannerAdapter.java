package com.renny.recyclerbanner.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.renny.recyclerbanner.R;

import java.util.List;

/**
 * Created by le on 2018/1/18.
 */

public class BannerAdapter extends PagerAdapter{

    private List<String> list;

    public BannerAdapter(List<String> dataList){
        this.list = dataList;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = View.inflate(container.getContext(), R.layout.item_image2,null);
        ImageView iv = v.findViewById(R.id.image);
        Glide.with(container.getContext()).load(list.get(position%list.size())).into(iv);
        container.addView(v);
        return v;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
