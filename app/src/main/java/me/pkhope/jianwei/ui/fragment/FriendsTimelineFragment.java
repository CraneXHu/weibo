package me.pkhope.jianwei.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.Constants;
import me.pkhope.jianwei.ui.activity.MainActivity;
import me.pkhope.jianwei.ui.adapter.FriendsTimelineAdapter;
import me.pkhope.jianwei.ui.base.BaseFragment;

/**
 * Created by pkhope on 2016/6/7.
 */
public class FriendsTimelineFragment extends BaseFragment {

    private int currentPage = 2;
    private List<Status> statusList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        adapter = new FriendsTimelineAdapter(getContext(),statusList);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadMore() {

        setRefreshing(true);
        MainActivity.getWeiboAPI().friendsTimeline(currentPage++, Constants.PAGE_COUNT, new RequestListener() {
            @Override
            public void onComplete(String s) {
                StatusList data = StatusList.parse(s);
                if (data.statusList == null){
                    return;
                }
                statusList.addAll(data.statusList);
                adapter.notifyDataSetChanged();

                setRefreshing(false);
            }

            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void refreshData() {

        setRefreshing(true);
        MainActivity.getWeiboAPI().friendsTimeline(1, Constants.PAGE_COUNT, new RequestListener() {
            @Override
            public void onComplete(String s) {

                statusList.clear();
                currentPage = 2;
                StatusList data = StatusList.parse(s);
                if (data.statusList == null){
                    return;
                }
                statusList.addAll(data.statusList);
                adapter.notifyDataSetChanged();

                setRefreshing(false);
            }

            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });
    }
}
