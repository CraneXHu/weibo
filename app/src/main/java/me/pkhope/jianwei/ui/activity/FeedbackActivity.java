package me.pkhope.jianwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVObject;

import me.pkhope.jianwei.R;

/**
 * Created by pkhope on 2016/6/22.
 */
public class FeedbackActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText feedbackEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feedback);

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

        feedbackEt = (EditText) findViewById(R.id.feedback_et);
        ImageView send = (ImageView) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVObject feedBack = new AVObject("feedback");
                feedBack.put("content",feedbackEt.getText());
                feedBack.saveInBackground();
                finish();
            }
        });
    }
}
