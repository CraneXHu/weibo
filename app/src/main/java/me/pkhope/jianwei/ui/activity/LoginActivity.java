package me.pkhope.jianwei.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import me.pkhope.jianwei.utils.AccessTokenPreference;
import me.pkhope.jianwei.Constants;
import me.pkhope.jianwei.R;

/**
 * Created by pkhope on 2016/6/14.
 */

public class LoginActivity extends Activity {

    private String sRedirectUri;
    private WebView mWeb;
    private String mLoginURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview_layout);
        mLoginURL = "https://open.weibo.cn/oauth2/authorize" + "?" + "client_id=" + Constants.APP_KEY
                + "&response_type=token&redirect_uri=" + Constants.REDIRECT_URL
                + "&key_hash=" + Constants.AppSecret + (TextUtils.isEmpty(Constants.PackageName) ? "" : "&packagename=" + Constants.PackageName)
                + "&display=mobile" + "&scope=" + Constants.SCOPE;
        sRedirectUri = Constants.REDIRECT_URL;
        mWeb = (WebView) findViewById(R.id.webview);
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWeb.setWebViewClient(new MyWebViewClient());
        mWeb.loadUrl(mLoginURL);
    }

    public void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (isUrlRedirected(url)) {
                view.stopLoading();
                handleRedirectedUrl(url);

            } else {
                    view.loadUrl(url);
            }
            return true;

        }

        @Override
        public void onPageStarted (WebView view, String url, Bitmap favicon){
            if (!url.equals("about:blank") && isUrlRedirected(url)) {
                view.stopLoading();
                    handleRedirectedUrl(url);
                    return;
            }
            super.onPageStarted(view, url, favicon);
        }
    }

    private void handleRedirectedUrl(String url){

        if (!url.contains("error")) {
            int tokenIndex = url.indexOf("access_token=");
            int expiresIndex = url.indexOf("expires_in=");
            int refresh_token_Index = url.indexOf("refresh_token=");
            int uid_Index = url.indexOf("uid=");

            String token = url.substring(tokenIndex + 13, url.indexOf("&", tokenIndex));
            String expiresIn = url.substring(expiresIndex + 11, url.indexOf("&", expiresIndex));
            String refresh_token = url.substring(refresh_token_Index + 14, url.indexOf("&", refresh_token_Index));
            String uid = new String();
            if (url.contains("scope=")) {
                uid = url.substring(uid_Index + 4, url.indexOf("&", uid_Index));
            } else {
                uid = url.substring(uid_Index + 4);
            }

            Oauth2AccessToken authToken = new Oauth2AccessToken();
            authToken.setUid(uid);
            authToken.setToken(token);
            authToken.setExpiresIn(expiresIn);
            authToken.setRefreshToken(refresh_token);
            AccessTokenPreference.saveAccessToken(getBaseContext(), authToken);

            startMainActivity();
        }
    }

    public boolean isUrlRedirected(String url) {
        return url.startsWith(sRedirectUri);
    }

}
