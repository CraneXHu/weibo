package me.pkhope.jianwei.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.weibo.sdk.openapi.models.Comment;
import com.sina.weibo.sdk.openapi.models.CommentList;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.ui.activity.MainActivity;
import me.pkhope.jianwei.ui.adapter.CommentAdapter;
import me.pkhope.jianwei.ui.base.BaseFragment;

/**
 * Created by pkhope on 2016/6/10.
 */
public class CommentToMeFragment extends BaseFragment {

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

        MainActivity.getWeiboAPI().toME(currentCursor,10,this);
    }

    @Override
    public void onComplete(String s) {
        super.onComplete(s);

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
