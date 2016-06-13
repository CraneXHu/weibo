package me.pkhope.jianwei.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.models.Comment;
import com.sina.weibo.sdk.openapi.models.CommentList;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.AccessTokenPreference;
import me.pkhope.jianwei.ui.activity.MainActivity;
import me.pkhope.jianwei.ui.adapter.CommentAdapter;
import me.pkhope.jianwei.Constants;
import me.pkhope.jianwei.R;
import me.pkhope.jianwei.ui.adapter.RecyclerViewDivider;
import me.pkhope.jianwei.api.MyCommentsAPI;
import me.pkhope.jianwei.ui.base.BaseFragment;

/**
 * Created by pkhope on 2016/6/10.
 */
public class CommentByMeFragment extends BaseFragment {

    private List<Comment> commentList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        adapter = new CommentAdapter(getContext(),commentList);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadPage() {
        setRefreshing(true);

        MainActivity.getWeiboAPI().byME(currentCursor,10,this);
    }

    @Override
    public void onComplete(String s) {
        CommentList data = CommentList.parse(s);
        if (data.commentList == null){
            return;
        }
        commentList.addAll(data.commentList);
        adapter.notifyDataSetChanged();
        currentCursor = Integer.parseInt(data.next_cursor);

        setRefreshing(false);
    }
}

