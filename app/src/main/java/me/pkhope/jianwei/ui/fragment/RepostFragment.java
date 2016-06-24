package me.pkhope.jianwei.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.models.Comment;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.App;
import me.pkhope.jianwei.Constants;
import me.pkhope.jianwei.interfaces.Identifier;
import me.pkhope.jianwei.ui.activity.MainActivity;
import me.pkhope.jianwei.ui.adapter.FriendsTimelineAdapter;
import me.pkhope.jianwei.ui.base.BaseFragment;

/**
 * Created by pkhope on 2016/6/19.
 */
public class RepostFragment extends BaseFragment{

    private int currentPage = 1;
    private List<Status> statusList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        adapter = new FriendsTimelineAdapter(getContext(),statusList);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadMore() {

        setRefreshing(true);
        App.getWeiboAPI().repostTimeline(Long.parseLong(((Identifier)getActivity()).getIdentifier()),currentPage++, Constants.PAGE_COUNT, new RequestListener() {
            @Override
            public void onComplete(String s) {
                setRefreshing(false);
                StatusList data = StatusList.parse(s);
                if (data.statusList == null){
                    return;
                }
                statusList.addAll(data.statusList);
                adapter.notifyDataSetChanged();

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
        App.getWeiboAPI().repostTimeline(Long.parseLong(((Identifier)getActivity()).getIdentifier()),1, Constants.PAGE_COUNT, new RequestListener() {

            @Override
            public void onComplete(String s) {
                setRefreshing(false);
                statusList.clear();
                currentPage = 2;
                StatusList data = StatusList.parse(s);
                if (data.statusList == null){
                    return;
                }
                statusList.addAll(data.statusList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });
    }
}
