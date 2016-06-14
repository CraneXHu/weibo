package me.pkhope.jianwei.ui.fragment;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import me.pkhope.jianwei.Constants;
import me.pkhope.jianwei.models.UserList;
import me.pkhope.jianwei.ui.activity.MainActivity;

/**
 * Created by pkhope on 2016/6/12.
 */
public class FollowersFragment extends FriendsFragment {

    @Override
    protected void loadMore() {
//        super.loadPage(page);
        setRefreshing(true);
        MainActivity.getWeiboAPI().follower(MainActivity.getWeiboAPI().getUid(), currentCursor, Constants.PAGE_COUNT, new RequestListener() {
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

        MainActivity.getWeiboAPI().follower(MainActivity.getWeiboAPI().getUid(), 0, Constants.PAGE_COUNT, new RequestListener() {
            @Override
            public void onComplete(String s) {

                userList.clear();
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
