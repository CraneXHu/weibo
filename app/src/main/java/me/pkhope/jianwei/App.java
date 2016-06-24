package me.pkhope.jianwei;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

import me.pkhope.jianwei.api.WeiboAPI;

/**
 * Created by pkhope on 2016/6/12.
 */
public class App extends Application {

    private static WeiboAPI weiboAPI = null;

    @Override
    public void onCreate() {
        super.onCreate();

        AVOSCloud.initialize(this, "YkRIg5nxMoflYSTb9sQMVfaq-gzGzoHsz", "6pbfFmJ1IK0gxc3aN2iT72UE");

    }

    public static void setWeiboAPI(WeiboAPI api){
       weiboAPI = api;
    }

    public static WeiboAPI getWeiboAPI(){
        return weiboAPI;
    }
}
