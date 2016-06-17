package me.pkhope.jianwei.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import me.pkhope.jianwei.R;
import me.pkhope.jianwei.ui.adapter.ViewPagerAdapter;
import me.pkhope.jianwei.widget.HackyViewPager;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by pkhope on 2016/6/17.
 */
public class ImageDetailActivity extends AppCompatActivity implements PhotoViewAttacher.OnPhotoTapListener{

    private TextView numTv;
    private HackyViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_detail);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        final List<String> images = intent.getStringArrayListExtra("images");
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,images);
        adapter.setPhotoTapListener(this);
        numTv = (TextView) findViewById(R.id.num_tv);
        viewPager = (HackyViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                numTv.setText((position+1) + "/" + images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (images.size() == 1){
            numTv.setVisibility(View.GONE);
        } else {
            numTv.setText(1 + "/" + images.size());
        }
    }

    @Override
    public void onPhotoTap(View view, float v, float v1) {
        finish();
    }

    @Override
    public void onOutsidePhotoTap() {
        finish();
    }
}
