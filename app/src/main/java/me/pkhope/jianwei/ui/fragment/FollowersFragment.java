package me.pkhope.jianwei.ui.fragment;

import me.pkhope.jianwei.ui.activity.MainActivity;

/**
 * Created by pkhope on 2016/6/12.
 */
public class FollowersFragment extends FriendsFragment {

    @Override
    protected void loadPage() {
//        super.loadPage(page);
        setRefreshing(true);
        MainActivity.getWeiboAPI().follower(MainActivity.getWeiboAPI().getUid(),currentCursor,10,this);
    }
}
