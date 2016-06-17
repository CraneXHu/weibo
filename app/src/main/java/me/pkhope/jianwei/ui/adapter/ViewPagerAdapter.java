package me.pkhope.jianwei.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by pkhope on 2016/6/17.
 */
public class ViewPagerAdapter extends PagerAdapter{

    private Context context;
    private List<String> images = new ArrayList<>();

    private PhotoViewAttacher.OnPhotoTapListener photoTapListener;

    public ViewPagerAdapter(Context context, List<String> images){
        this.context = context;
        this.images = images;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.image_detail, null);
        PhotoView pv = (PhotoView) view.findViewById(R.id.pv);
        pv.setOnPhotoTapListener(photoTapListener);
        Glide.with(context)
                .load(images.get(position).replace("thumbnail", "large"))
                .into(pv);
        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public void setPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener){
        photoTapListener = listener;
    }
}
