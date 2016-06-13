package me.pkhope.jianwei.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.ui.activity.MainActivity;
import me.pkhope.jianwei.ui.adapter.FriendsTimelineAdapter;
import me.pkhope.jianwei.ui.base.BaseFragment;

/**
 * Created by pkhope on 2016/6/7.
 */
public class FriendsTimelineFragment extends BaseFragment {

    private List<Status> statusList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        for (int i = 0; i < 10; i++){
            statusList.add(new Status());
        }
        adapter = new FriendsTimelineAdapter(getContext(),statusList);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadPage() {

        setRefreshing(true);
        MainActivity.getWeiboAPI().friendsTimeline(currentCursor, 10, this);
    }

    @Override
    public void onComplete(String s) {
        StatusList data = StatusList.parse(s);
        if (data.statusList == null){
            return;
        }
        statusList.addAll(data.statusList);
        adapter.notifyDataSetChanged();
        currentCursor = Integer.parseInt(data.next_cursor);

        setRefreshing(false);
    }
}
