package me.pkhope.jianwei.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import me.pkhope.jianwei.R;

/**
 * Created by pkhope on 2016/6/16.
 */
public class ReplyActivity extends AppCompatActivity implements RequestListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reply);

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

        final EditText et = (EditText) findViewById(R.id.content);

        Intent intent = getIntent();
        final String operation = intent.getStringExtra("operation");
        final String id = intent.getStringExtra("id");

        ImageView ibSend = (ImageView) findViewById(R.id.send);
        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (operation.equals("repost")){
                    MainActivity.getWeiboAPI().repost(Long.parseLong(id),et.getText().toString(),0,ReplyActivity.this);
                } else if (operation.equals("comment")){
                    MainActivity.getWeiboAPI().create(et.getText().toString(),Long.parseLong(id),false,ReplyActivity.this);
                }

            }
        });

    }

    @Override
    public void onComplete(String s) {
        finish();
    }

    @Override
    public void onWeiboException(WeiboException e) {
        e.printStackTrace();
    }
}
