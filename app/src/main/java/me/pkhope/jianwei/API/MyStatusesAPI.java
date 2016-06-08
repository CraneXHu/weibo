package me.pkhope.jianwei.API;

import android.content.Context;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;

/**
 * Created by pkhope on 2016/6/8.
 */
public class MyStatusesAPI {

    private StatusesAPI statusesAPI;

    public MyStatusesAPI(Context context, String appKey, Oauth2AccessToken token){
        statusesAPI = new StatusesAPI(context,appKey,token);
    }

    public void friendsTimeline(int page, int count, RequestListener listener){
        statusesAPI.friendsTimeline(0, 0, 10, 1, false, 0, false,listener);
    }
}
