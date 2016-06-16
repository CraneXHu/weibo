package me.pkhope.jianwei.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import me.pkhope.jianwei.R;

/**
 * Created by pkhope on 2016/6/16.
 */
public class SendWeiboActivity extends AppCompatActivity{

    public final static int REQUEST_CODE = 1;

    private Toolbar toolbar;
    private ImageView placeHolder;

    private ArrayList<String> photos = null;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_weibo);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        placeHolder = (ImageView) findViewById(R.id.place_holder);
        placeHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(SendWeiboActivity.this);
                intent.setPhotoCount(9);
                intent.setShowCamera(true);
                intent.setShowGif(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        final EditText et = (EditText) findViewById(R.id.content);

        ImageView ibSend = (ImageView) findViewById(R.id.send);
        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap == null){
                    return;
                }

                MainActivity.getWeiboAPI().upload(et.getText().toString(), bitmap, new RequestListener() {
                    @Override
                    public void onComplete(String s) {

                        finish();
                    }

                    @Override
                    public void onWeiboException(WeiboException e) {

                    }
                });
            }
        });


    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

                try {
                    FileInputStream fis = new FileInputStream(photos.get(0));
                    bitmap = BitmapFactory.decodeStream(fis);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return ;
                }
                placeHolder.setImageBitmap(bitmap);
            }
        }
    }
}
