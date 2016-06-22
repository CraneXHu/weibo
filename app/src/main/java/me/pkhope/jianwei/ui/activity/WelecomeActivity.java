package me.pkhope.jianwei.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import me.pkhope.jianwei.utils.AccessTokenPreference;
import me.pkhope.jianwei.R;

/**
 * Created by pkhope on 2016/6/14.
 */
public class WelecomeActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Oauth2AccessToken token = AccessTokenPreference.loadAccessToken(this);
        if (!token.isSessionValid()){
            intent = new Intent(WelecomeActivity.this,LoginActivity.class);
        } else {
            intent = new Intent(WelecomeActivity.this,MainActivity.class);
        }

        new SplashTask().execute();
    }

    class SplashTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try{

                Thread.sleep(500);

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            startActivity(intent);
            finish();
        }
    }
}

