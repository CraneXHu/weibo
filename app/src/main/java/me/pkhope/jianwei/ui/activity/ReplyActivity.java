package me.pkhope.jianwei.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import me.pkhope.jianwei.PostService;
import me.pkhope.jianwei.R;

/**
 * Created by pkhope on 2016/6/16.
 */
public class ReplyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText contentEt;

    private String operation;
    private long id;

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

        contentEt = (EditText) findViewById(R.id.content);

        Intent intent = getIntent();
        operation = intent.getStringExtra("operation");
        id = intent.getLongExtra("id",0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_send: {
                Intent intent = new Intent(ReplyActivity.this, PostService.class);
                intent.putExtra("operation",operation);
                intent.putExtra("content",contentEt.getText().toString());
                intent.putExtra("id",id);
                startService(intent);
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
