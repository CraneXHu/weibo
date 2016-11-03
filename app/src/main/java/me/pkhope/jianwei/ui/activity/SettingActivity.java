package me.pkhope.jianwei.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import me.pkhope.jianwei.R;
import me.pkhope.jianwei.utils.AccessTokenPreference;

/**
 * Created by pkhope on 2016/6/21.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

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

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean checked = sp.getBoolean("original",false);
        Switch switcher = (Switch) findViewById(R.id.swith);
        switcher.setChecked(checked);

        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("original",isChecked);
                editor.commit();
            }
        });

        View feedback = findViewById(R.id.ll_feedback);
        feedback.setOnClickListener(this);
        View about = findViewById(R.id.ll_about);
        about.setOnClickListener(this);
        View exit = findViewById(R.id.ll_exit);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_about: {
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.ll_feedback: {
                Intent intent = new Intent(SettingActivity.this, FeedbackActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.ll_exit: {
                AccessTokenPreference.clear(this);
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }
}

