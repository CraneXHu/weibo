package me.pkhope.jianwei.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.R;
import me.pkhope.jianwei.interfaces.Identifier;
import me.pkhope.jianwei.ui.adapter.MyFragmentPagerAdapter;
import me.pkhope.jianwei.ui.fragment.FollowersFragment;
import me.pkhope.jianwei.ui.fragment.FriendsFragment;
import me.pkhope.jianwei.ui.fragment.UserTimelineFragment;

/**
 * Created by pkhope on 2016/6/19.
 */
public class UserActivity extends AppCompatActivity implements Identifier{

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    private String name;

    private ImageView avatar;
    private ImageView gender;
    private TextView nickname;
    private TextView intro;

    private ViewPager viewPager;
    private MyFragmentPagerAdapter adapter;
    private TabLayout tabLayout;

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        init();

        Intent intent = getIntent();
        if (intent.getStringExtra("from").equals("text")){
            name = intent.getStringExtra("nickname").substring(1);
        } else {
            name = intent.getStringExtra("nickname");
        }


        MainActivity.getWeiboAPI().show(name, new RequestListener() {
            @Override
            public void onComplete(String s) {
                User user = User.parse(s);

                titleList.add(String.format("%d\n 微博",user.statuses_count));
                titleList.add(String.format("%d\n 粉丝",user.followers_count));
                titleList.add(String.format("%d\n 关注",user.friends_count));
                adapter.setTitleList(titleList);
                tabLayout.setupWithViewPager(viewPager);

                nickname.setText(user.name);
                intro.setText(user.description);

                Glide.with(UserActivity.this)
                        .load(user.profile_image_url)
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.default_image)
                        .into(avatar);
            }

            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });

    }

    protected void init(){

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        fragmentList.add(new UserTimelineFragment());
        fragmentList.add(new FollowersFragment());
        fragmentList.add(new FriendsFragment());
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        avatar = (ImageView) findViewById(R.id.avatar);
        nickname = (TextView) findViewById(R.id.nickname);
        gender = (ImageView) findViewById(R.id.gender);
        intro = (TextView) findViewById(R.id.intro);
    }

    @Override
    public String getIdentifier() {
        return name;
    }
}
