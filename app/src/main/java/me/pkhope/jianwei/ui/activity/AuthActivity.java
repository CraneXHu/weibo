package me.pkhope.jianwei.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import me.pkhope.jianwei.App;
import me.pkhope.jianwei.Constants;
import me.pkhope.jianwei.api.WeiboAPI;
import me.pkhope.jianwei.utils.AccessTokenPreference;
import me.pkhope.jianwei.utils.ToastUtils;

/**
 * Created by pkhope on 2016/6/24.
 */
public class AuthActivity  extends AppCompatActivity {

    private SsoHandler ssoHandler = null;

    private Oauth2AccessToken token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    protected void auth(){

        token = AccessTokenPreference.loadAccessToken(AuthActivity.this);
        if (token.getToken().equals("")){

            AuthInfo authInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
            ssoHandler = new SsoHandler(AuthActivity.this, authInfo);
            ssoHandler.authorize(new AuthListener());

        } else {

            WeiboAPI api = new WeiboAPI(this.getApplicationContext(),Constants.APP_KEY,token);
            App.setWeiboAPI(api);

            startMainActivity();
        }
    }

    public void startMainActivity(){
        Intent intent = new Intent(AuthActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    public class AuthListener implements WeiboAuthListener {

        public AuthListener(){
            ToastUtils.register(AuthActivity.this);
        }

        @Override
        public void onComplete(Bundle bundle) {
            Oauth2AccessToken token = Oauth2AccessToken.parseAccessToken(bundle);
            if (token.isSessionValid()){

                AccessTokenPreference.saveAccessToken(AuthActivity.this,token);
                WeiboAPI api = new WeiboAPI(AuthActivity.this.getApplicationContext(),Constants.APP_KEY,token);
                App.setWeiboAPI(api);

                startMainActivity();

            } else {

                String code = bundle.getString("code");
                String message = "Access failed";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                ToastUtils.showShort(message);
            }
        }

        @Override
        public void onCancel() {
            ToastUtils.showShort("Canceled");
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ToastUtils.showShort("Auth exception : " + e.getMessage());
        }
    }
}
