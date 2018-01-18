package com.renny.recyclerbanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.renny.recyclerbanner.banner.BannerLayout;
import com.renny.recyclerbanner.viewpager.BannerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BannerLayout.OnBannerItemClickListener {

    BannerLayout banner;
    BannerView bannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = findViewById(R.id.recycler);
        //解决recyclerView嵌套问题
        List<String> list = new ArrayList<>();
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/69427561.jpg");
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/23738150.jpg");
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/30127126.jpg");
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/36125492.jpg");

        banner.initBannerImageView(list);
//        bannerVertical.initBannerImageView(list);
        banner.setOnBannerItemClickListener(this);
        banner.setAutoPlaying(true);//默认true
        bannerView = findViewById(R.id.bannerView);
        bannerView.show(list);
    }


    public void jump(View view) {
        startActivity(new Intent(MainActivity.this, NormalActivity.class));
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "点击了第  " + position+"  项", Toast.LENGTH_SHORT).show();
    }
}
