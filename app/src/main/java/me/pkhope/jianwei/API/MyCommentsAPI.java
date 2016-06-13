package me.pkhope.jianwei.api;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.CommentsAPI;

/**
 * Created by pkhope on 2016/6/11.
 */
public class MyCommentsAPI {

    private CommentsAPI commentsAPI;

    public MyCommentsAPI(Context context, String appKey, Oauth2AccessToken token){
        commentsAPI = new CommentsAPI(context,appKey,token);
    }

    public void toME(int page, int count, RequestListener listener){
        commentsAPI.toME(0,0,count,page,0,0,listener);
    }

    public void byME(int page, int count, RequestListener listener){
        commentsAPI.byME(0,0,count,page,0,listener);
    }

    public void mentions(int page, int count, RequestListener listener){
        commentsAPI.mentions(0,0,count,page,0,0,listener);
    }
}
