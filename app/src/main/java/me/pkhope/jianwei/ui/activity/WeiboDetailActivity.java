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
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.R;
import me.pkhope.jianwei.interfaces.Identifier;
import me.pkhope.jianwei.ui.adapter.MyFragmentPagerAdapter;
import me.pkhope.jianwei.ui.fragment.CommentFragment;
import me.pkhope.jianwei.ui.fragment.FollowersFragment;
import me.pkhope.jianwei.ui.fragment.FriendsFragment;
import me.pkhope.jianwei.ui.fragment.RepostFragment;
import me.pkhope.jianwei.ui.fragment.UserTimelineFragment;

/**
 * Created by pkhope on 2016/6/19.
 */
public class WeiboDetailActivity extends AppCompatActivity implements Identifier{

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    private Toolbar toolbar;
    private String id;

    private ViewPager viewPager;
    private MyFragmentPagerAdapter adapter;
    private TabLayout tabLayout;

    private ImageView avatar;
    private ImageView image;
    private TextView nickname;
    private TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weibo_detail);
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
        id = intent.getStringExtra("id");

        MainActivity.getWeiboAPI().showWeiboDetail(Long.parseLong(id), new RequestListener() {
            @Override
            public void onComplete(String s) {
                Status status = Status.parse(s);

                titleList.add(String.format("%d\n 转发",status.reposts_count));
                titleList.add(String.format("%d\n 评论",status.comments_count));

                adapter.setTitleList(titleList);
                tabLayout.setupWithViewPager(viewPager);

                nickname.setText(status.user.screen_name);
                content.setText(status.text);

                Glide.with(WeiboDetailActivity.this)
                        .load(status.user.profile_image_url)
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.default_image)
                        .into(avatar);

                if (!status.original_pic.equals("")){

                    image.setVisibility(View.VISIBLE);
                    Glide.with(WeiboDetailActivity.this)
                            .load(status.original_pic)
                            .centerCrop()
                            .dontAnimate()
                            .thumbnail(0.5f)
//                    .override(holder.image.getMeasuredWidth(), holder.image.getMeasuredHeight())
                            .placeholder(R.drawable.default_image)
                            .into(image);
                }

            }

            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });

    }

    protected void init(){

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        fragmentList.add(new RepostFragment());
        fragmentList.add(new CommentFragment());
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        avatar = (ImageView) findViewById(R.id.avatar);
        nickname = (TextView) findViewById(R.id.nickname);
        content = (TextView) findViewById(R.id.content);
        image = (ImageView) findViewById(R.id.image);
    }

    @Override
    public String getIdentifier() {
        return id;
    }
}
