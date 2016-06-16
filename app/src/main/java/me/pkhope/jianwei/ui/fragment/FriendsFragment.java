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
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.Constants;
import me.pkhope.jianwei.models.UserList;
import me.pkhope.jianwei.ui.activity.MainActivity;
import me.pkhope.jianwei.ui.adapter.UserAdapter;
import me.pkhope.jianwei.ui.base.BaseFragment;

/**
 * Created by pkhope on 2016/6/12.
 */
public class FriendsFragment extends BaseFragment {

    protected int currentCursor = 1;
    protected List<User> userList;

    public FriendsFragment(){
        userList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        adapter = new UserAdapter(getContext(),userList);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadMore() {
//        super.loadPage(page);
        setRefreshing(true);
        MainActivity.getWeiboAPI().friends(MainActivity.getWeiboAPI().getUid(), currentCursor, Constants.PAGE_COUNT, new RequestListener() {
            @Override
            public void onComplete(String s) {

                UserList data = UserList.parse(s);
                if (data.userList == null){
                    return;
                }
                userList.addAll(data.userList);
                adapter.notifyDataSetChanged();
                currentCursor = Integer.parseInt(data.next_cursor);

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
        MainActivity.getWeiboAPI().friends(MainActivity.getWeiboAPI().getUid(), 0, Constants.PAGE_COUNT, new RequestListener() {
            @Override
            public void onComplete(String s) {

                userList.clear();
//                currentCursor = 1;
                UserList data = UserList.parse(s);
                if (data.userList == null){
                    return;
                }
                userList.addAll(data.userList);
                adapter.notifyDataSetChanged();
                currentCursor = Integer.parseInt(data.next_cursor);

                setRefreshing(false);
            }

            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });
    }
}
