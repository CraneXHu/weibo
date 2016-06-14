package me.pkhope.jianwei.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.models.Comment;
import com.sina.weibo.sdk.openapi.models.CommentList;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.Constants;
import me.pkhope.jianwei.ui.activity.MainActivity;
import me.pkhope.jianwei.ui.adapter.CommentAdapter;
import me.pkhope.jianwei.ui.base.BaseFragment;

/**
 * Created by pkhope on 2016/6/10.
 */
public class CommentToMeFragment extends BaseFragment {

    private int currentPage = 1;
    private List<Comment> commentList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        adapter = new CommentAdapter(getContext(),commentList);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void loadMore() {
        setRefreshing(true);

        MainActivity.getWeiboAPI().toME(currentPage++, Constants.PAGE_COUNT, new RequestListener() {
            @Override
            public void onComplete(String s) {
                CommentList data = CommentList.parse(s);
                if (data.commentList == null){
                    return;
                }
                commentList.addAll(data.commentList);
                adapter.notifyDataSetChanged();

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

        MainActivity.getWeiboAPI().toME(1, Constants.PAGE_COUNT, new RequestListener() {
            @Override
            public void onComplete(String s) {

                commentList.clear();
                currentPage = 2;
                CommentList data = CommentList.parse(s);
                if (data.commentList == null){
                    return;
                }
                commentList.addAll(data.commentList);
                adapter.notifyDataSetChanged();

                setRefreshing(false);
            }

            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });
    }
}
