package me.pkhope.jianwei.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.R;
import me.pkhope.jianwei.ui.activity.MainActivity;
import me.pkhope.jianwei.ui.adapter.MyFragmentPagerAdapter;

/**
 * Created by pkhope on 2016/6/7.
 */
public class MeFragment extends Fragment {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    private ImageView avatar;
    private ImageView gender;
    private TextView nickname;
    private TextView intro;

    MyFragmentPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragmentList.clear();
        titleList.clear();

        final ViewPager viewPager = (ViewPager)getView().findViewById(R.id.view_pager);
        fragmentList.add(new UserTimelineFragment());
        fragmentList.add(new FollowersFragment());
        fragmentList.add(new FriendsFragment());
        adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);

        final TabLayout tab = (TabLayout) getView().findViewById(R.id.tab);
        tab.setTabMode(TabLayout.MODE_FIXED);

        avatar = (ImageView) getView().findViewById(R.id.avatar);
        nickname = (TextView) getView().findViewById(R.id.nickname);
        gender = (ImageView) getView().findViewById(R.id.gender);
        intro = (TextView) getView().findViewById(R.id.intro);

        MainActivity.getWeiboAPI().show(MainActivity.getWeiboAPI().getUid(), new RequestListener() {
            @Override
            public void onComplete(String s) {

                User user = User.parse(s);

                titleList.add(String.format("%d\n 微博",user.statuses_count));
                titleList.add(String.format("%d\n 粉丝",user.followers_count));
                titleList.add(String.format("%d\n 关注",user.friends_count));
                adapter.setTitleList(titleList);
                tab.setupWithViewPager(viewPager);

                nickname.setText(user.name);
                intro.setText(user.description);

                Glide.with(getContext())
                        .load(user.profile_image_url)
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
//                .override(holder.avatar.getMeasuredWidth(), holder.avatar.getMeasuredHeight())
                        .placeholder(R.drawable.default_image)
                        .into(avatar);

            }

            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });
    }
}
