package me.pkhope.jianwei;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private SsoHandler ssoHandler;

    private Toolbar toolbar;

    private ViewPager viewPager;

    private BottomNavigationBar bottomNavigationBar;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        initViewPager();

        initBottomBar();


        Oauth2AccessToken token = AccessTokenPreference.loadAccessToken(MainActivity.this);
        if (token.getToken().equals("")){

            AuthInfo authInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
            ssoHandler = new SsoHandler(MainActivity.this, authInfo);
            ssoHandler.authorize(new AuthListener(MainActivity.this));

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    protected void initViewPager(){

        fragmentList.add(new WeiboFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new MeFragment());

        viewPager = (ViewPager)findViewById(R.id.main_viewpager);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList));
//        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected void initBottomBar(){

        bottomNavigationBar = (BottomNavigationBar)findViewById(R.id.bottom_nav_bar);
        bottomNavigationBar
                .setActiveColor(R.color.bottom_item_active)
                .setInActiveColor(R.color.bottom_item_inactive)
                .setBarBackgroundColor(R.color.bottom_nav_bg);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_home_white_24dp, "Home"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "Books"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_favorite_white_24dp, "Favorite"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
