package me.pkhope.jianwei;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;

/**
 * Created by pkhope on 2016/6/7.
 */
public class WeiboFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weibo, container, false);
        final TextView tv = (TextView) view.findViewById(R.id.result);

        Oauth2AccessToken token = AccessTokenPreference.loadAccessToken(getContext());
        StatusesAPI sa = new StatusesAPI(getActivity(),Constants.APP_KEY,token);
        sa.friendsTimeline(0, 0, 10, 1, false, 0, false, new RequestListener() {
            @Override
            public void onComplete(String s) {
                tv.setText(s);
            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        });
        return view;
    }
}
