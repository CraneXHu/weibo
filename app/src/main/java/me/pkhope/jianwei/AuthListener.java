package me.pkhope.jianwei;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Created by pkhope on 2016/6/3.
 */
public class AuthListener implements WeiboAuthListener {

    private Context context;

    AuthListener(Context context){
        this.context = context;
        ToastUtils.register(context);
    }

    @Override
    public void onComplete(Bundle bundle) {
        Oauth2AccessToken token = Oauth2AccessToken.parseAccessToken(bundle);
        if (token.isSessionValid()){
            AccessTokenPreference.saveAccessToken(context,token);
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
